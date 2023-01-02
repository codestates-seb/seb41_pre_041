import "@toast-ui/editor/dist/toastui-editor.css";
import { Link } from "react-router-dom";
import { Editor } from "@toast-ui/react-editor";
import { useRef, useState } from "react";
import styled from "styled-components";
import axios from "../../api/axios";
import { useParams } from "react-router-dom";

const EditorWrap = styled.div`
  border-radius: 4px;
`;

const ErrorMassage = styled.p`
  margin-top: 4px;
  padding: 2px;
  font-size: 12px;
  color: #d0393e;
`;

const AnswerButton = styled.button`
  background: #0a95ff;
  border: transparent;
  border-radius: 4px;
  margin: 10px 0px 15px 0px;
  padding: 10px;

  color: #ffffff;

  &:hover {
    background: #0074cc;
  }
`;

function AnswerForm({ isLogin }) {
  const { id } = useParams();
  const answerRef = useRef();
  const [isError, setIsError] = useState(false);
  const [body, setBody] = useState("");
  const bodyLength = body.length;

  const onChange = () => {
    const data = answerRef.current.getInstance().getMarkdown();
    data.length <= 30 ? setIsError(true) : setIsError(false);
    setBody(data);
  };

  const onSubmit = () => {
    if (!isError) {
      addAnswer(id, body);
    }
  };

  const addAnswer = async (id, body) => {
    await axios
      .post("/api/answers", {
        questionId: id,
        content: body,
      })
      .then(() => {
        window.location.reload();
      })
      .catch((error) => {
        console.log(error);
      });
  };

  return (
    <>
      <EditorWrap error={isError}>
        <Editor
          ref={answerRef}
          initialValue={" "}
          height="300px"
          autofocus={false}
          initialEditType="wysiwyg"
          previewStyle="tab"
          toolbarItems={[
            ["heading", "bold", "italic", "code", "strike"],
            ["link", "quote", "codeblock", "image", "table"],
            ["ol", "ul", "hr"],
          ]}
          onChange={onChange}
        />
        {isError && (
          <ErrorMassage>
            Body must be at least 30 characters; you entered {bodyLength}
          </ErrorMassage>
        )}
      </EditorWrap>
      {isLogin ? (
        <AnswerButton onClick={onSubmit}>Post Your Answer</AnswerButton>
      ) : (
        <Link to="/login">
          <AnswerButton>Post Your Answer</AnswerButton>
        </Link>
      )}
    </>
  );
}

export default AnswerForm;
