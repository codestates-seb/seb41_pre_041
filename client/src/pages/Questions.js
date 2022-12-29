import { Link } from "react-router-dom";
import React, { useState, useEffect } from "react";
import styled from "styled-components";
import Paging from "../components/Paging";
import axios from "axios";

const Questions = () => {
  const [questionData, setQuestionData] = useState({});

  const fetchQuestions = async () => {
    try {
      const res = await axios.get(
        `${process.env.REACT_APP_API_URL}/api/questions`
      );
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
            <Link to="/ask">Ask Question</Link>
          </AskButton>
        </h1>
      </Header>
      <Quantity>
        <h4>{questionData.length} questions</h4>
      </Quantity>
      <div className="question">
        {Object.keys(questionData).map((key) => (
          <Ask key={key}>
            <Stats>
              <div>{questionData[key].voteCount} votes</div>
              <div>{questionData[key].answerCount} answers</div>
              <div>{questionData[key].viewCount} views</div>
            </Stats>
            <Title>
              <Link to={`/question/${key}`}>
                <h3>{questionData[key].title}</h3>
              </Link>
              <div className="question-summary">
                {questionData[key].content.slice(0, 40)}
              </div>
            </Title>
            <UserContainer>
              <span>{questionData[key].memberName}</span>{" "}
              <span>{questionData[key].createdAt.slice(0, 10)}</span>
            </UserContainer>
          </Ask>
        ))}
      </div>
      <Paging />
    </Layout>
  );
};

const Layout = styled.div`
  display: flex;
  flex-direction: column;
  width: 100%;
  height: 85vh;
  margin: 0 auto;
  padding: 3rem;
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
  width: 100%;
  height: auto;
  margin: 9px 0;
  padding: 9px 0;
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
    color: black;
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
