import { Link } from "react-router-dom";
import React, { useState, useEffect } from "react";
import styled from "styled-components";
import PaginationQ from "../components/PaginationQ";
import axios from "axios";
import { Viewer } from "@toast-ui/react-editor";
// import "@toast-ui/editor/dist/toastui-editor-viewer.css";

const Questions = () => {
  const [questionData, setQuestionData] = useState([]);
  const [totalQuestions, setTotalQuestions] = useState("");
  const [limit, setLimit] = useState(15);
  const [page, setPage] = useState(1);

  const fetchQuestions = async () => {
    try {
      const res = await axios.get(
        `${process.env.REACT_APP_API_URL}/api/questions?page=${page}&limit=${limit}`
      );
      setQuestionData(res.data.data);
    } catch (error) {
      console.log(error);
    }
  };

  const fetchTotalQuestions = async () => {
    try {
      const res = await axios.get(
        `${process.env.REACT_APP_API_URL}/api/questions`
      );
      setTotalQuestions(res.data.pageInfo.totalElements);
    } catch (error) {
      console.log(error);
    }
  };

  useEffect(() => {
    fetchQuestions();
    fetchTotalQuestions();
  }, [page, limit]);

  return (
    <Layout>
      <Header>
        <h1 className="all-questions">
          All Questions
          <AskButton>
            {" "}
            <Link to="/ask">Ask Question</Link>
          </AskButton>
        </h1>
      </Header>
      <Quantity>
        <h4>{totalQuestions} questions</h4>
      </Quantity>
      <div className="question">
        {questionData.map((question) => (
          <Ask key={question.id}>
            <Stats>
              <div>{question.voteCount} votes</div>
              <div>{question.answerCount} answers</div>
              <div>{question.viewCount} views</div>
            </Stats>
            <Title>
              <Link to={`/question/${question.id}`}>
                <h3>{question.title}</h3>
              </Link>
              <div className="question-summary">
                <Viewer
                  events={["load", "change"]}
                  initialValue={`${question.content}`.slice(0, 100)}
                />
              </div>
            </Title>
            <UserContainer>
              <span>{question.memberName}</span>
              <time>{`${question.createdAt}`.slice(0, 10)}</time>
            </UserContainer>
          </Ask>
        ))}
      </div>
      <PaginationQ
        totalQuestions={totalQuestions}
        limit={limit}
        page={page}
        setPage={setPage}
      />
    </Layout>
  );
};

const Layout = styled.div`
  display: flex;
  flex-direction: column;
  width: 100%;
  height: 85vh;
  margin: 0 auto;
  padding: 3em 3em;
`;

const Header = styled.div`
  padding: 15px 7px 20px 10px;
`;

const Quantity = styled.div`
  padding: 15px 7px 25px 15px;
`;

const Ask = styled.div`
  display: flex;
  justify-content: flex-start;
  flex-direction: row;
  flex: none;
  border-top: 1px solid rgb(220, 224, 226);
`;

const AskButton = styled.button`
  background-color: #0a95ff;
  color: white;
  border-radius: 3px;
  width: 110px;
  height: 40px;
  border: 1px solid #39739d;
  margin: 0 5px;
  float: right;

  & a {
    color: white;
  }

  &:hover {
    background: #339af0;
  }
`;

const Stats = styled.div`
  flex: 2;
  flex-direction: column;
  padding: 7px 7px 7px 50px;
`;

const Title = styled.div`
  width: 700px;
  flex-direction: column;
  padding: 7px;
  overflow-wrap: break-word;
  & h3 {
    font-size: 21px;
    margin-bottom: 15px;
  }
  > a {
    color: #3b4045;
  }
`;

const UserContainer = styled.div`
  flex: 4;
  width: 200px;
  margin-top: auto;
  margin-left: auto;
  padding: 7px;
  display: flex;
  justify-content: space-between;
`;

export default Questions;
