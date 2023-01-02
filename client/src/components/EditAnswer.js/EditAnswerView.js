import axios from "../../api/axios";
import styled from "styled-components";
import { useNavigate, useParams } from "react-router-dom";
import { Editor } from "@toast-ui/react-editor";
import { useState, useEffect, useRef } from "react";
import "@toast-ui/editor/dist/toastui-editor.css";

const EditASection = styled.section`
  width: 800px;
  .edit-answer-notice {
    margin-bottom: 30px;
    padding: 20px;
    border: 1px solid #f0e7c9;
    border-radius: 5px;
    background-color: #fdf7e2;
    font-size: 17px;
  }

  .question-inform {
    margin-bottom: 20px;
    > span {
      color: #1392db;
      font-size: 22px;
    }
    > p {
      margin-top: 20px;
      font-size: 18px;
    }
  }

  h3 {
    margin-bottom: 10px;
    font-size: 25px;
    font-weight: 500;
  }

  .toastui-editor-contents {
    font-size: 17px;
  }

  .button-box > button {
    width: 105px;
    height: 47px;
    border: none;
    border-radius: 5px;
    background-color: #81c7fc;
    font-size: 16px;
    margin: 20px 0 50px;
    color: #ffffff;
  }

  .button-box > .update-btn {
    background-color: #0a95ff;
    :hover {
      cursor: pointer;
      background-color: #0074cc;
    }
  }

  .button-box > .cancle-btn {
    margin-left: 20px;
    border: none;
    background-color: transparent;
    color: #1392db;
    :hover {
      cursor: pointer;
      color: #0074cc;
    }
  }
`;

const EditAnswerView = () => {
  const { id } = useParams();
  const editARef = useRef();
  const navigate = useNavigate();
  const [newAnswer, setnewAnswer] = useState(false);
  const [singleA, setSingleA] = useState({});
  const [singleQ, setSingleQ] = useState({});
  const [content, setContent] = useState("");

  const updateAnswer = () => {
    const data = editARef.current.getInstance().getMarkdown();

    if (data.length < 20) {
      setnewAnswer(false);
    } else {
      setnewAnswer(true);
    }
    setContent(data);
  };

  const getSingleA = async () => {
    await axios
      .get(`${process.env.REACT_APP_API_URL}/api/answers/${id}`)
      .then((response) => {
        console.log(response.data);
        setSingleA(response.data);
      })
      .catch((error) => {
        console.log(error);
      });
  };

  const getSingleQ = async () => {
    await axios
      .get(
        `${process.env.REACT_APP_API_URL}/api/questions/${singleA.questionId}`
      )
      .then((response) => {
        setSingleQ(response.data);
        console.log(singleQ);
      })
      .catch((error) => {
        console.log(error);
      });
  };

  useEffect(() => {
    if (singleA.content) {
      editARef.current.getInstance().setMarkdown(singleA.content);
    }
  }, [singleA]);

  useEffect(() => {
    getSingleA();
    getSingleQ();
  }, []);

  const handleUpdate = async () => {
    if (setContent.length) {
      await axios
        .patch(`/api/answers/${id}`, {
          content,
        })
        .then(() => {
          navigate(-1);
        })
        .catch((error) => {
          console.log(error);
        });
    }
  };

  const handleCancel = () => {
    navigate(-1);
  };

  return (
    <EditASection>
      <div className="edit-answer-notice">
        <p>Your edit will be placed in a queue until it is peer reviewed.</p>
        <br />
        <p>
          We welcome edits that make the post easier to understand and more
          valuable for readers. Because community members review edits, please
          try to make the post substantially better than how you found it, for
          example, by fixing grammar or adding additional resources and
          hyperlinks.
        </p>
      </div>
      <div className="question-inform">
        <span className="question-title">{singleQ.title}</span>
        <p className="question-body">{singleQ.content}</p>
      </div>
      <h3>Answer</h3>
      <Editor
        id="input-body"
        initialValue={content}
        previewStyle="vertical"
        height="300px"
        initialEditType="wysiwyg"
        useCommandShortcut={false}
        ref={editARef}
        onChange={() => updateAnswer()}
      />
      <div className="button-box">
        <button
          disabled={!newAnswer}
          onClick={handleUpdate}
          className={newAnswer ? "update-btn" : ""}
        >
          Save edits
        </button>
        <button className="cancle-btn" onClick={handleCancel}>
          Cancle
        </button>
      </div>
    </EditASection>
  );
};

export default EditAnswerView;
