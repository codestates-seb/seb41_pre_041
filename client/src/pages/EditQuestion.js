import React, { useState, useRef, useEffect } from "react";
import { Editor } from '@toast-ui/react-editor';
import '@toast-ui/editor/dist/toastui-editor.css';
import styled from 'styled-components';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';

const EditQuestion = () => {
  const [title, setTitle] = useState('');
  const [content, setContent] = useState('');
  const [qData, setQData] = useState({});
  const { navigate } = useNavigate();
  const editorRef = useRef();

  const updateQuestion = () => {
    const data = editorRef.current.getInstance().getMarkdown();
    setContent(data);
  };

  const getQuestion = async () => {
    try {
      const res = await axios.get(`${process.env.REACT_APP_API_URL}/api/questions/${qData.id}`);
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
      await axios.patch(`${process.env.REACT_APP_API_URL}/api/questions/${qData.id}`, {
        title: title,
        content: content,
      });
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
        <div className="edit-title"><div>Title</div>
          <input value={qData.title} onChange={event => setTitle(event.target.value)} />
        </div>
      </TitleContainer>
      <div className="edit-question">
        <div>Body</div>
        <Editor
          ref={editorRef}
          initialValue={`${qData.content}`}
          previewStyle="vertical"
          height="300px"
          initialEditType="wysiwyg"
          onChange={() => updateQuestion()}
          toolbarItems={[
            ['heading', 'bold', 'italic', 'strike'],
            ['hr', 'quote'],
            ['ul', 'ol', 'task', 'indent', 'outdent'],
            ['table', 'image', 'link'],
            ['code', 'codeblock']
          ]}
          useCommandShortcut={false}
        ></Editor>
        <button onClick={handleSubmit} >Save edits</button>
        <button onClick={handleCancel}>Cancel</button>

      </div>
    </div >
  );
};

const headPrecaution = `Your edit will be placed in queue until it is peer reviewed.

  We welcome edits that make the post easier to understand and more valuable for readers.
  Because community members review edits, please try to make the post substantially better than
  how you found it, for example, by fixing grammar or adding additional resources and hyperlinks.`


const Precaution = styled.div`
white-space: pre-line;
background: rgb(255, 248, 220);
width: 100vw
padding: 10px;
border: 1px solid rgb(220, 224, 226);
`;

const TitleContainer = styled.div`
& input {
  width: 100%;
  line-height: 30px;
}
`

export default EditQuestion;
