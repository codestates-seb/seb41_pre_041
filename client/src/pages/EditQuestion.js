import React, { useState, useRef, useEffect } from "react";
import { Editor } from '@toast-ui/react-editor';
import '@toast-ui/editor/dist/toastui-editor.css';
import styled from 'styled-components';
import axios from 'axios';

const dummyPost = {
  id: 1,
  title: '기존 제목',
  question: '기존 포스팅'
}

const EditQuestion = () => {
  const [title, setTitle] = useState(dummyPost.title);
  const [post, setPost] = useState(dummyPost.question);
  const [qData, setQData] = useState();


  const editorRef = useRef();
  // const handleEditClick = () => {
  //   // 입력창에 입력한 내용을 HTML 태그 형태로 취득
  //   console.log(editorRef.current?.getInstance().getHTML());
  //   // 입력창에 입력한 내용을 MarkDown 형태로 취득
  //   console.log(editorRef.current?.getInstance().getMarkdown());
  // };
  const getQuestion = async () => {
    await axios
      .get('/questions/{question-id}')
      .then((res) => {
        setQData([res.data.body]);
        // 리덕스??
        // dispatch(setTitle(res.data.body.question.title));
        // dispatch(setPost(res.data.body.question.question));
      })
      .catch((err) => {
        console.log(err)
      })
  }
  useEffect(() => {
    console.log('useEffect')
    getQuestion();
  }, [])

  const editSubmit = async () => {
    await axios
      .patch('/questions/{question-id}', {
        title: title,
        question: post,
      })
      .then((res) => {
        console.log(res)
      })
  }


  const headPrecaution = `Your edit will be placed in queue until it is peer reviewed.

  We welcome edits that make the post easier to understand and more valuable for readers.
  Because community members review edits, please try to make the post substantially better than
  how you found it, for example, by fixing grammar or adding additional resources and hyperlinks.`


  return (
    <div>
      <Precaution>{headPrecaution}</Precaution>
      <TitleContainer>
        <div className="edit-title"><div>Title</div>
          <input value={title} onChange={event => setTitle(event.target.value)} />
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
          toolbarItems={[
            // 툴바 옵션 설정
            ['heading', 'bold', 'italic', 'strike'],
            ['hr', 'quote'],
            ['ul', 'ol', 'task', 'indent', 'outdent'],
            ['table', 'image', 'link'],
            ['code', 'codeblock']
          ]}
          useCommandShortcut={false} // 키보드 입력 컨트롤 방지
        ></Editor>
        <button onClick={editSubmit} onChange={event => setPost(event.target.value)}>Save edits</button>
        <button onClick={() => setPost(false)}>Cancel</button>

      </div>
    </div >
  );
};

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
