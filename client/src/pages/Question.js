import { Link } from "react-router-dom";

const Question = () => {
  return <div>여기는 질문글 페이지!
    <Link to="/ask"><button>Ask Question</button></Link>
    <Link to="/edit/question"><div>Edit(질문글에 있을 부분!)</div></Link>
    <Link to="/edit/answer"><div>Edit(답변에 있을 부분!)</div></Link>
  </div>;
};

export default Question;
