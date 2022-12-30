import React, { useState, useRef, useEffect } from "react";
import { Editor } from "@toast-ui/react-editor";
import "@toast-ui/editor/dist/toastui-editor.css";
import styled from "styled-components";
import axios from "../api/axios";
import { useNavigate, useParams } from "react-router-dom";

const EditQuestion = () => {
  const { id } = useParams();
  const [title, setTitle] = useState("");
  const [content, setContent] = useState("");
  const [qData, setQData] = useState({});
  const navigate = useNavigate();
  const editorRef = useRef();

  const updateQuestion = () => {
    const data = editorRef.current.getInstance().getMarkdown();
    setContent(data);
  };

  const getQuestion = async () => {
    try {
      const res = await axios.get(
        `${process.env.REACT_APP_API_URL}/api/questions/${id}`
      );
      setQData(res.data);
    } catch (err) {
      console.error(err);
    }
  };

  useEffect(() => {
    getQuestion();
  }, []);

  useEffect(() => {
    if (qData.title) {
      setTitle(qData.title);
    }
  }, [qData]);
  useEffect(() => {
    if (qData.content) {
      editorRef.current.getInstance().setMarkdown(qData.content);
    }
  }, [qData]);

  const editSubmit = async () => {
    try {
      await axios.patch(
        `${process.env.REACT_APP_API_URL}/api/questions/${id}`,
        {
          title: title,
          content: content,
        }
      );
    } catch (err) {
      console.error(err);
    }
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    editSubmit();
    navigate(-1);
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
      <EditorContainer>
        <div className="edit-question">
          <div>Body</div>
          <Editor
            ref={editorRef}
            initialValue={content}
            previewStyle="vertical"
            height="300px"
            initialEditType="wysiwyg"
            onChange={() => updateQuestion()}
            useCommandShortcut={false}
          ></Editor>

          <SaveButton onClick={handleSubmit}>Save edits</SaveButton>
          <CancelButton onClick={handleCancel}>Cancel</CancelButton>
        </div>
      </EditorContainer>
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
  width: 90wv;
  padding: 20px;
  border: 1px solid rgb(220, 224, 226);
`;

const TitleContainer = styled.div`
padding: 10px;
  & input {
    width: 780px;
    line-height: 30px;
  }
`;

const EditorContainer = styled.div`
padding: 10px;
width: 800px;
`;

const SaveButton = styled.button`
    width: 105px;
    height: 47px;
    border: none;
    border-radius: 5px;
    background-color: #81c7fc;
    font-size: 16px;
    margin: 20px 5px 50px 0;
    color: #ffffff;
    background-color: #0a95ff;
    :hover {
      cursor: pointer;
      background-color: #0074cc;
    }
`
const CancelButton = styled.button`
    width: 105px;
    height: 47px;
    border: none;
    border-radius: 5px;
    background-color: #81c7fc;
    font-size: 16px;
    color: #ffffff;
    background-color: #0a95ff;
    :hover {
      cursor: pointer;
      background-color: #0074cc;
    }
`

export default EditQuestion;
