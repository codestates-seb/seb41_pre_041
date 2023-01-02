import styled from "styled-components";
import AskView from "../components/Ask/AskView";
import Footer from "../components/Footer";

const AskArticle = styled.article`
  display: flex;
  flex-direction: column;
  background-color: #f8f9f9;
`;

const Ask = () => {
  return (
    <>
      <AskArticle>
        <AskView />
      </AskArticle>
      <Footer />
    </>
  );
};

export default Ask;
