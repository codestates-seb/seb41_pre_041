import styled from "styled-components";
import { Editor } from "@toast-ui/react-editor";
import { useState, useEffect, useRef } from "react";
import AskImg from "../../assets/AskImg.png";
import "@toast-ui/editor/dist/toastui-editor.css";

const AskSection = styled.section`
  .ask-section {
    width: 1264px;
    margin: 0px auto;
  }

  .ask-header {
    display: flex;
    justify-content: space-between;
    align-items: center;
    > h1 {
      font-size: 35px;
      font-weight: 600;
    }
  }

  .ask-notice {
    margin-top: 10px;
    width: 860px;
    border: solid 1px #bad9f0;
    border-radius: 5px;
    background-color: #ebf4fb;
    padding: 25px;
    > h2 {
      font-size: 25px;
      font-weight: 400;
      margin-bottom: 10px;
    }
  }

  .ask-notice-content {
    margin-bottom: 15px;
    font-size: 18px;
    > span {
      color: #0b79ce;
    }
  }

  .steps-title {
    margin-bottom: 8px;
    font-size: 18px;
    font-weight: 600;
  }

  .steps-content {
    padding-left: 24px;
    > li {
      list-style-type: disc;
      font-size: 16px;
    }
  }

  .ask-form {
    width: 860px;
    padding: 20px;
    margin: 10px 10px 10px 0;
    display: flex;
    flex-direction: column;
    background-color: #ffffff;
    border: 1px solid #e8ebec;
    border-radius: 5px;
  }

  .label-input {
    font-size: 18px;
    font-weight: 600;
    margin-bottom: 7px;
  }

  .input-notice {
    margin-top: 6px;
    font-size: 15px;
    margin-bottom: 16px;
  }

  .warning {
    font-weight: 500;
    color: #c22e32;
  }

  #input-title {
    padding: 8px 10px;
    width: 100%;
    height: 37px;
    border: 1px solid #a7a7a7;
    border-radius: 3px;
    font-size: 17px;
    &:focus {
      border: 1px solid #92c3e9;
      box-shadow: 0px 0px 0px 4px #d9eaf7;
      outline: 0;
    }
  }

  .toastui-editor-contents {
    font-size: 19px;
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

const AskView = () => {
  const askRef = useRef();
  const [title, setTitle] = useState(false);
  const [body, setBody] = useState(false);
  const [titleValue, setTitleValue] = useState("");
  const [bodyValue, setBodyValue] = useState("");

  const hadleTitle = (e) => {
    if (e.target.value.length < 15) {
      setTitle(false);
    } else {
      setTitle(true);
    }
    setTitleValue(e.target.value);
  };

  const handleBody = () => {
    const data = askRef.current.getInstance().getMarkdown();

    if (data.length < 20) {
      setBody(false);
    } else {
      setBody(true);
    }
    setBodyValue(data);
  };

  const handlePost = () => {
    if (titleValue.length && bodyValue.length) {
      const post = [titleValue, bodyValue];
      console.log(post);
    }
  };

  const handleReset = () => {
    window.location.reload();
  };

  useEffect(() => {
    window.onbeforeunload = function pushRefresh() {
      window.scrollTo(0, 0);
    };
  }, []);

  return (
    <AskSection>
      <section className="ask-section">
        {/*질문글 작성 페이지 제목*/}
        <div className="ask-header">
          <h1>Ask a public question</h1>
          <img src={AskImg} alt="질문 작성 페이지" />
        </div>
        {/*질문글 작성 페이지 주의사항*/}
        <div className="ask-notice">
          <h2>Writing a good question</h2>
          <p className="ask-notice-content">
            You’re ready to <span>ask</span> a{" "}
            <span>programming-related question</span> and this form will help
            guide you through the process. Looking to ask a non-programming
            question? See <span>the topics here</span> to find a relevant site.
          </p>
          <h5 className="steps-title">Steps</h5>
          <ul className="steps-content">
            <li>Summarize your problem in a one-line title.</li>
            <li>Describe your problem in more detail.</li>
            <li>Describe what you tried and what you expected to happen.</li>
            <li>
              Add “tags” which help surface your question to members of the
              community.
            </li>
            <li>Review your question and post it to the site.</li>
          </ul>
        </div>
        {/*질문글 작성 폼*/}
        <div className="ask-form">
          <label htmlFor="input-title" className="label-input">
            Title
          </label>
          <p className="input-notice">
            Be specific and imagine you’re asking a question to another person.{" "}
            <span className="warning">Minimum 15 characters.</span>
          </p>
          <input
            id="input-title"
            placeholder="e.g. Is there an R function for finding the index of an element in a vector?"
            value={titleValue || ""}
            onChange={(e) => hadleTitle(e)}
          />
        </div>
        <div className="ask-form">
          <label htmlFor="input-body" className="label-input">
            What are the details of your problem & What did you try and what
            were you expecting?
          </label>
          <p className="input-notice">
            Introduce the problem and expand on what you put in the title first.{" "}
            <br />
            And Describe what you tried, what you expected to happen, and what
            actually resulted.{" "}
            <span className="warning">Minimum 20 characters.</span>
          </p>
          <Editor
            id="input-body"
            initialValue=" "
            previewStyle="vertical"
            height="600px"
            initialEditType="wysiwyg"
            useCommandShortcut={false}
            ref={askRef}
            onChange={() => handleBody()}
          />
        </div>
        <div className="button-box">
          <button
            disabled={!title || !body}
            onClick={handlePost}
            className={title && body ? "ask-active" : null}
          >
            Post your question
          </button>
          <button
            className={
              titleValue.length || bodyValue.length
                ? "reset-active"
                : "reset-disable"
            }
            onClick={handleReset}
          >
            Discard draft
          </button>
        </div>
      </section>
    </AskSection>
  );
};
export default AskView;
