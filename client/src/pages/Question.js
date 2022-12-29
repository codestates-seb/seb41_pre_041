import axios from "axios";
import instance from "../api/axios";
import { useState, useEffect } from "react";
import { Link, useParams } from "react-router-dom";
import styled from "styled-components";
import AnswerForm from "../components/AnswerForm";
// import Pagination from "../components/Pagination";

// CSS
const QuestionContainer = styled.div`
  display: flex;
  flex-direction: column;
  width: 900px;

  padding: 24px;
  border-left: 1px solid #d6d9dc;
`;

const QuestionHeader = styled.div`
  display: flex;
  align-items: center;
  justify-content: space-between;
  margin-bottom: 8px;

  h1 {
    font-size: 27px;
    width: 700px;
    overflow-wrap: break-word;
  }
  a {
    color: #3b4045;
  }
`;

const AskButton = styled.button`
  width: 120px;
  padding: 10px;
  color: #ffffff;
  font-size: 13px;
  border: 1px solid #39739d;
  border-radius: 4px;
  background: #0a95ff;

  &:hover {
    background: #0074cc;
  }
`;

const AskDate = styled.div`
  display: flex;

  font-size: 13px;
  color: #6a737c;
  border-bottom: 1px solid #d6d9dc;
  padding-bottom: 8px;
  margin-bottom: 16px;

  div {
    margin: 0px 16px 8px 0px;
  }
  time {
    color: #232629;
  }
`;

const Section = styled.section`
  display: flex;
  flex-direction: column;
`;

const QuestionArea = styled.div`
  display: flex;
  width: 100%;
`;

const LeftBtn = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: space-around;
  padding-right: 16px;

  button {
    height: 36px;
    background: inherit;
    border: none;
    border-radius: 0;
    padding: 0;
    overflow: visible;
    cursor: pointer;

    &:active {
      .icon {
        fill: #f48225;
      }
    }
  }

  .icon {
    fill: #babfc4;
  }
  .count {
    font-size: 21px;
    color: #6a737c;
  }
`;

const Content = styled.div`
  display: flex;
  flex-direction: column;
  width: 100%;

  .post-area {
    width: 700px;
    overflow-wrap: break-word;
    font-size: 15px;
    margin: 10px 0px;
  }
  .writer-area {
    display: flex;
    justify-content: space-between;
    margin: 16px 0px;

    font-size: 13px;

    span {
      margin: 4px;
      color: #6a737c;
    }
    a {
      margin: 4px;
      color: #6a737c;
    }
    span:first-child {
      margin-left: 0px;
    }
  }

  .user-info {
    display: flex;
    flex-direction: column;
    width: 200px;
    padding: 7px;
    border-radius: 4px;
    background: #d9eaf7;

    .asked {
      margin-bottom: 4px;
      color: #6a737c;
    }

    .user-container {
      display: flex;
      align-items: center;
    }

    .user {
      margin: 4px 6px 0px 0px;
    }

    .user-name {
      color: #0074cc;
    }
  }

  .flex {
    display: flex;
    flex-direction: column;
  }
`;

const AnswerArea = styled.div`
  display: flex;
  flex-direction: column;

  h1 {
    color: #3b4045;
  }

  .answer-count {
    margin: 20px 0px;
  }
`;

const AnswerContent = styled.div`
  display: flex;
  padding: 16px 0px;
  border-bottom: 1px solid #e3e6e8;
`;

const AnswerCreate = styled.div`
  .answer-header {
    margin: 20px 0px;
  }
`;

const DeleteButton = styled.button`
  background: #0a95ff;
  border: transparent;
  border-radius: 4px;
  margin-top: 10px;
  padding: 10px;
  color: #ffffff;

  &:hover {
    background: #0074cc;
  }
`;

function Question({ isLogin }) {
  const { id } = useParams();
  const [singleQ, setSingleQ] = useState({});
  const [dataA, setdataA] = useState([]);

  // pagination
  // const [posts, setPosts] = useState([]);
  // const [currentPage, setCurrentPage] = useState(1);
  // const [postsPerPage, setPostPerPage] = useState(5);

  // useEffect(() => {
  //   const fetchData = async () => {
  //     const response = await axios.get(

  //     );
  //     setPosts(response.data)
  //   };
  //   fetchData();
  // }, []);

  // const indexOfLast = currentPage * postsPerPage;
  // const indexOfFirst = indexOfLast - postsPerPage;
  // const currentPosts = (posts) => {
  //   let currentPosts = 0;
  //   currentPosts = posts.slice(indexOfFirst, indexOfLast);
  //   return currentPosts;
  // }
  /*단일 질문글 정보 받아오기*/

  const getSingleQ = async () => {
    await axios
      .get(`${process.env.REACT_APP_API_URL}/api/questions/${id}`)
      .then((response) => {
        setSingleQ(response.data);
        console.log(singleQ);
      })
      .catch((error) => {
        console.log(`ERROR RESPONSE : ${error.status}`);
      });
  };

  const getDataA = async () => {
    await axios
      .get(`${process.env.REACT_APP_API_URL}/api/answers`)
      .then((response) => {
        setdataA(response.data.data);
        console.log(dataA);
      })
      .catch((error) => {
        console.log(`ERROR RESPONSE : ${error.status}`);
      });
  };

  useEffect(() => {
    getSingleQ();
    getDataA();
  }, []);

  const removeQuestion = async () => {
    await instance
      .delete(`/api/questions/${id}`)
      .then(() => {
        getSingleQ();
      })
      .catch((error) => {
        console.log(`ERROR RESPONSE : ${error.status}`);
      });
  };

  const removeAnswer = async () => {
    await axios
      .delete(`/api/answers/${dataA.id}`)
      .then(() => {
        getSingleQ();
      })
      .catch((error) => {
        console.log(`ERROR RESPONSE : ${error.status}`);
      });
  };

  return (
    <QuestionContainer>
      <QuestionHeader>
        <h1>
          <Link to={`/question/${id}`}>{singleQ.title}</Link>
        </h1>
        <div>
          <Link to="/ask">
            <AskButton>Ask Question</AskButton>
          </Link>
        </div>
      </QuestionHeader>
      <AskDate>
        <div>
          Asked <time>{singleQ.createdAt.slice(0, 10)}</time>
        </div>
        <div>
          Modified <time>{singleQ.updateAt.slice(0, 10)}</time>
        </div>
        <div>
          Viewd <time>{singleQ.view}</time>
        </div>
      </AskDate>

      <Section>
        <QuestionArea>
          <LeftBtn>
            <button>
              <svg className="icon" width="36" height="36" viewBox="0 0 36 36">
                <path d="M2 25h32L18 9 2 25Z"></path>
              </svg>
            </button>
            <div className="count">1</div>
            <button>
              <svg className="icon" width="36" height="36" viewBox="0 0 36 36">
                <path d="M2 11h32L18 27 2 11Z"></path>
              </svg>
            </button>
          </LeftBtn>

          {/* 게시글 내용 */}
          <Content>
            <div className="post-area">
              <p>{singleQ.content}</p>
            </div>
            <div className="writer-area">
              <div>
                <span>Share</span>
                <Link to={`/edit/question/${singleQ.id}`}>Edit</Link>
                <span>Follow</span>
              </div>
              <div>
                <span>
                  Edited <time>{singleQ.updateAt.slice(0, 10)}</time>
                </span>
              </div>
              <div className="flex">
                <div className="user-info">
                  <span className="asked">
                    Asked <time>{singleQ.createdAt.slice(0, 10)}</time>
                  </span>
                  <div className="user-container">
                    <div className="user">
                      <svg
                        className="user-icon"
                        width="24"
                        heigth="24"
                        viewBox="0 0 1000 1000"
                      >
                        <path d="M500,10C227,10,10,227,10,500s217,490,490,490s490-217,490-490S773,10,500,10z M500,206c77,0,140,63,140,140c0,77-63,140-140,140c-77,0-140-63-140-140C360,269,423,206,500,206z M801,773c-77,77-182,133-301,133s-224-49-301-133c-21-21-21-56,0-77c77-84,182-140,301-140s224,56,301,140C822,717,822,752,801,773z" />
                      </svg>
                    </div>
                    <span className="user-name">{singleQ.author}</span>
                  </div>
                </div>
                <DeleteButton onClick={removeQuestion}>Delete</DeleteButton>
              </div>
            </div>
          </Content>
        </QuestionArea>
        <AnswerArea>
          {/* 답변 갯수 */}
          {/* if 답변이 등록되어 있다면, 등록된 갯수를 출력한다. */}
          <div className="answer-count">
            <h1>{/*카운트 된 갯수*/} Answer</h1>
          </div>
          {/* 답변 내용 */}
          {/* if 답변이 등록되어 있다면, 등록된 답변을 출력한다. */}
          {dataA.map((singleA) => (
            <AnswerContent key={singleA.id}>
              <LeftBtn>
                <button>
                  <svg
                    className="icon"
                    width="36"
                    height="36"
                    viewBox="0 0 36 36"
                  >
                    <path d="M2 25h32L18 9 2 25Z"></path>
                  </svg>
                </button>
                <div className="count">1</div>
                <button>
                  <svg
                    className="icon"
                    width="36"
                    height="36"
                    viewBox="0 0 36 36"
                  >
                    <path d="M2 11h32L18 27 2 11Z"></path>
                  </svg>
                </button>
              </LeftBtn>
              <Content>
                <div className="post-area">
                  <p>{singleA.content}</p>
                </div>
                <div className="writer-area">
                  <div>
                    <span>Share</span>
                    <Link to={`/edit/answer/${singleA.id}`}>Edit</Link>
                    <span>Follow</span>
                  </div>
                  <div>
                    <span>
                      Edited <time>{singleA.updateAt.slice(0, 10)}</time>
                    </span>
                  </div>
                  <div className="flex">
                    <div className="user-info">
                      <span className="asked">
                        Asked <time>{singleA.createdAt.slice(0, 10)}</time>
                      </span>
                      <div className="user-container">
                        <div className="user">
                          <svg
                            className="user-icon"
                            width="24"
                            heigth="24"
                            viewBox="0 0 1000 1000"
                          >
                            <path d="M500,10C227,10,10,227,10,500s217,490,490,490s490-217,490-490S773,10,500,10z M500,206c77,0,140,63,140,140c0,77-63,140-140,140c-77,0-140-63-140-140C360,269,423,206,500,206z M801,773c-77,77-182,133-301,133s-224-49-301-133c-21-21-21-56,0-77c77-84,182-140,301-140s224,56,301,140C822,717,822,752,801,773z" />
                          </svg>
                        </div>
                        <span className="user-name">{singleA.author}</span>
                      </div>
                    </div>
                    <DeleteButton onClick={removeAnswer}>Delete</DeleteButton>
                  </div>
                </div>
              </Content>
            </AnswerContent>
          ))}
          {/* 답변 작성 */}
          {/* 답변을 작성한다. */}
          <AnswerCreate>
            <div className="answer-header">
              <h1>Your Answer</h1>
            </div>
            <AnswerForm isLogin={isLogin} />
          </AnswerCreate>
          {/* <Pagination/> */}
        </AnswerArea>
      </Section>
    </QuestionContainer>
  );
}

export default Question;
