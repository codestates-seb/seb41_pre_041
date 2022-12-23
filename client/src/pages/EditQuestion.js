import React, { useState, useRef } from "react";
import { Editor } from '@toast-ui/react-editor';
import '@toast-ui/editor/dist/toastui-editor.css';
import styled from 'styled-components';

const EditQuestion = () => {
  const [title, setTitle] = useState('기존 제목');
  const [post, setPost] = useState('기존포스팅');

  const editorRef = useRef();
  const handleEditClick = () => {
    // 입력창에 입력한 내용을 HTML 태그 형태로 취득
    console.log(editorRef.current?.getInstance().getHTML());
    // 입력창에 입력한 내용을 MarkDown 형태로 취득
    console.log(editorRef.current?.getInstance().getMarkdown());
  };

  const headPrecaution = `Your edit will be placed in queue until it is peer reviewed.

  We welcome edits that make the post easier to understand and more valuable for readers.
  Because community members review edits, please try to make the post substantially better than
  how you found it, for example, by fixing grammar or adding additional resources and hyperlinks.`

  const sideHead = `How to edit`

  const sidePrecaution =
    `• Correct minor types or mistakes
  • Clarify meaning without changing it
  • Add related resources or links
  • Always respect the author’s intent
  • Don’t use edits to reply to the author`

  return (
    <div>
      <Precaution>{headPrecaution}
        <SidePrecaution>{sideHead}<br />{sidePrecaution}</SidePrecaution></Precaution>
      <div className="edit-title"><div>Title</div>
        <input value={title} onChange={event => setTitle(event.target.value)} />
      </div>
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
        <button onClick={handleEditClick}>Save edits</button>
        <button>Cancel</button>

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


const SidePrecaution = styled.div`
float: right;
white-space: pre-line;
width: 300px;
background: rgb(255, 248, 220);
padding: 10px;
`;

export default EditQuestion;
