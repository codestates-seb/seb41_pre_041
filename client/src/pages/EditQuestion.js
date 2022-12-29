import React, { useState, useRef, useEffect } from "react";
import { Editor } from "@toast-ui/react-editor";
import "@toast-ui/editor/dist/toastui-editor.css";
import styled from "styled-components";
import axios from "axios";
import { useNavigate } from "react-router-dom";

// const dummyPost = {
//   id: 1,
//   title: '기존 제목',
//   question: '기존 포스팅'
// }

const EditQuestion = () => {
  const [title, setTitle] = useState("");
  const [post, setPost] = useState("");
  const [qData, setQData] = useState({});
  const { navigate } = useNavigate();
  const editorRef = useRef();

  const updateQuestion = () => {
    const data = editorRef.current.getInstance().getMarkdown();
    setPost(data);
  };

  const getQuestion = async () => {
    try {
      const res = await axios.get(
        `${process.env.REACT_APP_API_URL}/api/questions/{question-id}`
      );
      setQData(res.data);
    } catch (err) {
      console.error(err);
    }
  };

  useEffect(() => {
    getQuestion();
  }, []);

  const editSubmit = async () => {
    try {
      await axios.patch(
        `${process.env.REACT_APP_API_URL}/api/questions/{question-id}`,
        {
          title,
          content: post,
        }
      );
    } catch (err) {
      console.error(err);
    }
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    editSubmit();
    navigate(`${process.env.REACT_APP_API_URL}/api/questions/`);
  };

  const handleCancel = () => {
    navigate(-1);
  };

  return (
    <div>
      <Precaution>{headPrecaution}</Precaution>
      <TitleContainer>
        <div className="edit-title">
          <div>Title</div>
          <input
            value={title}
            onChange={(event) => setTitle(event.target.value)}
          />
        </div>
      </TitleContainer>
      <div className="edit-question">
        <div>Body</div>
        <Editor
          ref={editorRef} // DOM 선택용 useRef
          initialValue={post}
          placeholder="내용을 입력해주세요."
          previewStyle="vertical" // 미리보기 스타일 지정
          height="300px" // 에디터 창 높이
          initialEditType="wysiwyg" // 초기 입력모드 설정(디폴트 markdown)
          onChange={() => updateQuestion()}
          toolbarItems={[
            // 툴바 옵션 설정
            ["heading", "bold", "italic", "strike"],
            ["hr", "quote"],
            ["ul", "ol", "task", "indent", "outdent"],
            ["table", "image", "link"],
            ["code", "codeblock"],
          ]}
          useCommandShortcut={false} // 키보드 입력 컨트롤 방지
        ></Editor>
        <button onClick={handleSubmit}>Save edits</button>
        <button onClick={handleCancel}>Cancel</button>
      </div>
    </div>
  );
};

const headPrecaution = `Your edit will be placed in queue until it is peer reviewed.

  We welcome edits that make the post easier to understand and more valuable for readers.
  Because community members review edits, please try to make the post substantially better than
  how you found it, for example, by fixing grammar or adding additional resources and hyperlinks.`;

const Precaution = styled.div`
  white-space: pre-line;
  background: rgb(255, 248, 220);
  width: 100vw;
  padding: 10px;
  border: 1px solid rgb(220, 224, 226);
`;

const TitleContainer = styled.div`
  & input {
    width: 100%;
    line-height: 30px;
  }
`;

export default EditQuestion;
