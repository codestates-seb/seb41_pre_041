import "@toast-ui/editor/dist/toastui-editor.css";
import { Link } from "react-router-dom";
import { Editor } from "@toast-ui/react-editor";
import { useRef, useState } from "react";
import styled from "styled-components";
import axios from "../api/axios";
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

const LoginMessage = styled.span`
  font-size: 15px;
  margin-left: 10px;
  a {
    color: #0074cc;
  }
`;

function AnswerForm({ initialValue, onClickHandler, isLogin }) {
  const { params } = useParams();
  const answerRef = useRef();
  const [answerData, setAnswerData] = useState([]);
  const [isError, setIsError] = useState(false);
  const [body, setBody] = useState("");
  const bodyLength = body.length;

  const onChange = () => {
    const data = answerRef.current.getInstance().getMarkdown();
    data.length <= 30 ? setIsError(true) : setIsError(false);
    setBody(data);
  };

  const onSubmit = () => {
    !isError && onClickHandler(body);
    answerRef.current.getInstance().setMarkdown("");
    setIsError(false);
  };

  const addAnswer = async () => {
    await axios
      .post("/api/answers", {
        params,
        content: body,
      })
      .then((res) => {
        setAnswerData(res.data.body);
        console.log(answerData);
      })
      .catch((err) => {
        console.log(err);
      });
  };

  return (
    <>
      <EditorWrap error={isError}>
        <Editor
          ref={answerRef} // 값을 저장
          initialValue={initialValue || " "}
          height="300px"
          autofocus={false}
          initialEditType="wysiwyg"
          previewStyle="tab"
          toolbarItems={[
            // 툴바에 들어갈것들
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
      <AnswerButton onClick={onSubmit}>Post Your Answer</AnswerButton>
      {!isLogin ? null : (
        <LoginMessage>
          <Link to="/signup">Sign up</Link>
          &nbsp;or&nbsp;
          <Link to="/login">Log in</Link>
        </LoginMessage>
      )}
    </>
  );
}

export default AnswerForm;
