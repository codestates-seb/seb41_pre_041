import { Link } from "react-router-dom";
import React from 'react';
import styled from 'styled-components';
import Paging from '../components/Paging';

// 테스트용 더미데이터
const Question = [{
  id: 1,
  user: '김코딩',
  title: '어려워서 모르겠습니다.',
  question: '어떻게 하는 지 모르겠어요',
  createdAt: "2022-12-20",
  votes: 5,
  answers: 1,
  views: 41,
},
{
  id: 2,
  user: '박해커',
  title: '그래도 잘 해보아요',
  question: '매핑은 그럭저럭 해봅니다.',
  createdAt: "2022-12-21",
  votes: 103,
  answers: 3,
  views: 3002,
}];

const Questions = () => {
  return (
    <Layout>
      <div>
        <Header>
          <h1 className="all-questions">All Questions
            <AskButton> <Link to="/ask">Ask Question</Link></AskButton></h1></Header>
        <Quantity><h4>{Question.length} questions</h4></Quantity>
        <div className="question">
          {Question.map(question => (
            <Ask key={question.id}>
              <Stats>
                <div>{question.votes} votes</div>
                <div>{question.answers} answers</div>
                <div>{question.views} views</div>
              </Stats>
              <Title><Link to="/question"><h3>{question.title}</h3></Link>
                <div className="question-summary">{question.question}</div></Title>
              <UserContainer>
                <span>{question.user}</span>
                <time>{question.createdAt}</time>
              </UserContainer>

            </Ask>
          ))}
        </div>
      </div>
      <Paging />
    </Layout>
  );
};

const Layout = styled.div`
display: flex;
flex-direction: column;
width: 100%;
margin: 0 auto;
padding: 3em 3em;
`;

const Header = styled.div`
padding: 15px 7px 20px 10px
`;

const Quantity = styled.div`
padding: 15px 7px 25px 15px
`;

const Ask = styled.div`
display: flex;
justify-content: flex-start;
flex-direction: row;
flex: none;
border-top: 1px solid rgb(220, 224, 226);
`;

const AskButton = styled.button`
background-color: #137EFF;
text: white;
border-radius: 2px;
width: 94px;
height: 36px;
border: 1px;
margin: 0 5px;
float: right;


&:hover{
background: #339af0;
}
&:active{
background: #1c7ed6;
}
`;

const Stats = styled.div`
flex-direction: column;
padding: 7px 7px 7px 50px;
`;

const Title = styled.div`
flex-direction: column;
padding: 7px
`;

const UserContainer = styled.div`
margin-top: auto;
margin-left: auto;
padding: 7px
`;

export default Questions;
