import styled from "styled-components";
import EditAnswerView from "../components/EditAnswer.js/EditAnswerView";

const EditAArticle = styled.article`
  height: 85vh;
  padding: 40px;
  display: flex;
`;

const EditAnswer = () => {
  return (
    <EditAArticle>
      <EditAnswerView />
    </EditAArticle>
  );
};

export default EditAnswer;
