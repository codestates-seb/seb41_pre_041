import axios from "axios";
import instance from "../api/axios";
import { useState, useEffect } from "react";
import { Link, useParams } from "react-router-dom";
import styled from "styled-components";
import AnswerForm from "../components/Question/AnswerForm";
import AnswerVoteForm from "../components/Question/AnswerVoteForm";
import PaginationA from "../components/PaginationA";

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

  .btn_frame {
    height: 36px;
    background: inherit;
    border: none;
    padding: 0;
    overflow: visible;
  }

  .count {
    font-size: 21px;
    color: #6a737c;
  }

  .icon {
    cursor: pointer;
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
  const [dataA, setDataA] = useState([]);

  // pagination
  const [posts, setPosts] = useState([]); // 게시글의 갯수
  const [currentPage, setCurrentPage] = useState(1); // 페이지네이션의 페이지
  const [postsPerPage, setPostPerPage] = useState(2); // 1 페이지당 보여줄 게시글의 갯수

  // vote (isVote: 서버에 등록된 투표 이력, isClickVote: 현재 누른 버튼)
  const [isVotedQ, setIsVotedQ] = useState("");
  const [voteA, setVoteA] = useState(0);

  useEffect(() => {
    const fetchData = async () => {
      const response = await axios.get(
        `${process.env.REACT_APP_API_URL}/api/answers`,
        { params }
      );
      setPosts(response.data.data);
    };
    fetchData();
  }, []);

  const indexOfLast = currentPage * postsPerPage;
  const indexOfFirst = indexOfLast - postsPerPage;
  const currentPosts = (posts) => {
    let currentPosts = 0;
    currentPosts = posts.slice(indexOfFirst, indexOfLast);
    return currentPosts;
  };

  /*단일 질문글 받아오기*/
  const getSingleQ = async () => {
    await axios
      .get(`${process.env.REACT_APP_API_URL}/api/questions/${id}`)
      .then((response) => {
        setSingleQ(response.data);
      })
      .catch((error) => {
        console.log(error);
      });
  };

  /*답변 목록 받아오기*/
  const params = { questionId: id };
  const getDataA = async () => {
    await axios
      .get(`${process.env.REACT_APP_API_URL}/api/answers`, { params })
      .then((response) => {
        setDataA(response.data.data);
      })
      .catch((error) => {
        console.log(error);
      });
  };

  /*질문 삭제*/
  const removeQuestion = async () => {
    await instance
      .delete(`/api/questions/${id}`)
      .then(() => {
        window.location.replace("/questions");
      })
      .catch((error) => {
        console.log(error);
      });
  };

  /*답변 삭제*/
  const removeAnswer = async (id) => {
    await instance
      .delete(`/api/answers/${id}`)
      .then(() => {
        getDataA();
      })
      .catch((error) => {
        console.log(error);
      });
  };

  /*질문글 투표 여부 체크*/
  const checkVoteQ = async () => {
    await instance
      .get(`api/questions/${id}/votes/me`)
      .then((response) => {
        setIsVotedQ(response.data.voteStatus);
      })
      .catch(() => {
        console.log("질문에 투표를 하지 않았습니다.");
      });
  };

  /*투표 정보 즉각 업데이트 (투표 post 또는 patch 할 때마다 발생하도록)*/
  useEffect(() => {
    getSingleQ();
    checkVoteQ();
  }, [isVotedQ]);

  /*질문글 투표(post) 또는 재투표(patch)*/
  const handleVoteClickQ = (e) => {
    /*첫 투표*/
    if (isVotedQ === "" && e.target.id) {
      postVoteadQ(e.target.id);
    } else if (isVotedQ !== e.target.id) {
      /*재투표(표 변경)*/
      patchVoteadQ(e.target.id);
    } else {
      /*재투표(표 취소:: 현재 투표 상태와 누른 버튼 id가 동일할 때)*/
      patchVoteadQ("NO_VOTE");
    }
  };

  const postVoteadQ = async (click) => {
    await instance
      .post(`api/questions/${id}/votes`, {
        voteStatus: click,
      })
      .then(() => {
        checkVoteQ();
      })
      .catch((error) => {
        console.log(error);
      });
  };

  const patchVoteadQ = async (click) => {
    await instance
      .patch(`api/questions/${id}/votes/me`, {
        voteStatus: click,
      })
      .then(() => {
        checkVoteQ();
      })
      .catch((error) => {
        console.log(error);
      });
  };

  /*답변 투표에 따른 상태 변경 발생할 때마다 재실행*/
  useEffect(() => {
    getDataA();
  }, [voteA]);

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
          Asked <time>{`${singleQ.createdAt}`.slice(0, 10)}</time>
        </div>
        <div>
          Modified <time>{`${singleQ.modifiedAt}`.slice(0, 10)}</time>
        </div>
        <div>
          Viewd <time>{singleQ.viewCount}</time>
        </div>
      </AskDate>

      <Section>
        <QuestionArea>
          <LeftBtn>
            <div className="btn_frame">
              <svg
                className="icon"
                width="36"
                height="36"
                viewBox="0 0 36 36"
                fill={isVotedQ === "UPVOTE" ? "#f48225" : "#babfc4"}
              >
                <path
                  id="UPVOTE"
                  d="M2 25h32L18 9 2 25Z"
                  onClick={(e) => {
                    handleVoteClickQ(e);
                  }}
                ></path>
              </svg>
            </div>
            <div className="count">{singleQ.voteCount}</div>
            <div className="btn_frame">
              <svg
                className="icon"
                width="36"
                height="36"
                viewBox="0 0 36 36"
                fill={isVotedQ === "DOWNVOTE" ? "#f48225" : "#babfc4"}
              >
                <path
                  id="DOWNVOTE"
                  d="M2 11h32L18 27 2 11Z"
                  onClick={(e) => {
                    handleVoteClickQ(e);
                  }}
                ></path>
              </svg>
            </div>
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
                  Edited <time>{`${singleQ.modifiedAt}`.slice(0, 10)}</time>
                </span>
              </div>
              <div className="flex">
                <div className="user-info">
                  <span className="asked">
                    Asked <time>{`${singleQ.createdAt}`.slice(0, 10)}</time>
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
                    <span className="user-name">{singleQ.memberName}</span>
                  </div>
                </div>
                <DeleteButton onClick={removeQuestion}>Delete</DeleteButton>
              </div>
            </div>
          </Content>
        </QuestionArea>
        <AnswerArea>
          {/* 답변 갯수 */}
          <div className="answer-count">
            <h1>{dataA.length} Answer</h1>
          </div>
          {/* 답변 내용 */}
          {currentPosts(dataA).map((singleA) => (
            <AnswerContent key={singleA.id}>
              <AnswerVoteForm
                id={singleA.id}
                voteCount={singleA.voteCount}
                voteA={voteA}
                setVoteA={setVoteA}
              />
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
                      Edited <time>{`${singleA.modifiedAt}`.slice(0, 10)}</time>
                    </span>
                  </div>
                  <div className="flex">
                    <div className="user-info">
                      <span className="asked">
                        Asked <time>{`${singleA.createdAt}`.slice(0, 10)}</time>
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
                        <span className="user-name">{singleA.memberName}</span>
                      </div>
                    </div>
                    <DeleteButton onClick={() => removeAnswer(singleA.id)}>
                      Delete
                    </DeleteButton>
                  </div>
                </div>
              </Content>
            </AnswerContent>
          ))}
          {dataA.length === 0 ? null : (
            <PaginationA
              postsPerPage={postsPerPage}
              totalPosts={posts.length}
              paginate={setCurrentPage}
              className="pagination"
            />
          )}
          {/* 답변 작성 */}
          {/* 답변을 작성한다. */}
          <AnswerCreate>
            <div className="answer-header">
              <h1>Your Answer</h1>
            </div>
            <AnswerForm isLogin={isLogin} />
          </AnswerCreate>
        </AnswerArea>
      </Section>
    </QuestionContainer>
  );
}
export default Question;
