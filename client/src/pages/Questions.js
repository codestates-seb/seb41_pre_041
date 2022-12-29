import { Link } from "react-router-dom";
import React, { useState, useEffect } from "react";
import styled from "styled-components";
import Paging from "../components/Paging";
import axios from "axios";

const Questions = () => {
  const [questionData, setQuestionData] = useState([]);


  const fetchQuestions = async () => {
    try {
      const res = await axios.get(`${process.env.REACT_APP_API_URL}/api/questions`);
      setQuestionData(res.data.data);
    } catch (err) {
      console.error(err);
    }
  };

  useEffect(() => {
    fetchQuestions();
  }, []);


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
        <h4>{questionData.length} questions</h4>
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
              <div className="question-summary">{question.content}</div>
            </Title>
            <UserContainer>
              <span>{question.memberName}</span>
              <time>{question.createdAt}</time>
            </UserContainer>
          </Ask>
        ))}
      </div>
      <Paging
        totalPosts={questionData.length}
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
  background-color: #137eff;
  color: white;
  border-radius: 2px;
  width: 94px;
  height: 36px;
  border: 1px;
  margin: 0 5px;
  float: right;

  &:hover {
    background: #339af0;
  }
  &:active {
    background: #1c7ed6;
  }
`;

const Stats = styled.div`
  flex-direction: column;
  padding: 7px 7px 7px 50px;
`;

const Title = styled.div`
  flex-direction: column;
  padding: 7px;
`;

const UserContainer = styled.div`
  margin-top: auto;
  margin-left: auto;
  padding: 7px;
`;

export default Questions;
