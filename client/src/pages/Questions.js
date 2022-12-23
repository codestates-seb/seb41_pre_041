import { Link } from "react-router-dom";

const Questions = () => {
  return <div>여기는 질문 리스트 페이지!
    <Link to="/question"><div>임시 링크! 질문글 하나를 클릭했을 때 해당 질문 상세 페이지로 넘어가는 기능을 임시로 구현했습니다. 요구 기능에 맞춰 수정 반드시 필요!</div></Link>
  </div>;
};

export default Questions;
