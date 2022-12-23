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
    width: 170px;
    height: 47px;
    border: none;
    border-radius: 5px;
    background-color: #81c7fc;
    font-size: 16px;
    margin: 20px 0 50px;
    color: #ffffff;
  }

  .button-box > .ask-active {
    background-color: #0a95ff;
    :hover {
      cursor: pointer;
      background-color: #0074cc;
    }
  }

  .button-box > .reset-active {
    background-color: transparent;
    color: #c22e32;
  }

  .button-box > .reset-disable {
    display: none;
  }
`;

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

  const handleUpdate = () => {
    if (newAnswerValue.length) {
      const updateNewAnswer = [newAnswerValue];
      console.log(updateNewAnswer);
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
        initialValue="기존 답변 내용이 들어가야 합니다"
        /*get으로 기존 답변 데이터를 가져와 내용 속성을 해당 부분에 삽입*/
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
          className={newAnswer ? "update-btn" : null}
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
