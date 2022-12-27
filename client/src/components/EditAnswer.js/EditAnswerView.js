import axios from "axios";
import styled from "styled-components";
import { Link } from "react-router-dom";
import { Editor } from "@toast-ui/react-editor";
import { useState, useRef } from "react";
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

  .cancle-btn {
    font-size: 16px;
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

const answerDummy = {
  answerId: 1,
  content: "요구사항 정의서부터 작성하세요. 화이팅~",
};

const EditAnswerView = () => {
  const editARef = useRef();
  const [newAnswer, setnewAnswer] = useState(false);
  const [newAnswerValue, setNewAnswerValue] = useState("");

  const updateAnswer = () => {
    const data = editARef.current.getInstance().getMarkdown();

    if (data.length < 20) {
      setnewAnswer(false);
    } else {
      setnewAnswer(true);
    }
    setNewAnswerValue(data);
  };

  const handleUpdate = async () => {
    if (newAnswerValue.length) {
      await axios
        .patch(`/answer/${answerDummy.answerId}`, {
          content: newAnswerValue,
        })
        .then(() => {
          /*해당 답변의 질문글 상세 페이지로 이동하도록 수정 필요*/
          window.location.replace("/");
        })
        .catch((error) => {
          console.log(`ERROR RESPONSE : ${error.status}`);
        });
    }
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
        <span className="question-title">해당 답변의 제목</span>
        <p className="question-body">해당 답변의 내용</p>
      </div>
      <h3>Answer</h3>
      <Editor
        id="input-body"
        initialValue={answerDummy.content}
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
        <Link to="/">
          <button className="cancle-btn">Cancle</button>
        </Link>
      </div>
    </EditASection>
  );
};

export default EditAnswerView;
