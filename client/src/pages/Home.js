import styled from "styled-components";
import Background from "../assets/home.jpg";
import HomeIcon from "../assets/homeicon.png";

const HomeFrame = styled.section`
  height: 100vh;
  display: flex;
  justify-content: center;
  align-items: center;
  background-image: url(${Background});
  background-size: cover;
`;

const TopFrame = styled.div`
  width: 1000px;
  height: 800px;
  padding: 10px;
  display: flex;
  justify-content: center;
  align-items: center;
  background-color: white;
  border: 3px solid #ff69b4;
`;

const MiddleFrame = styled.div`
  width: 900px;
  height: 700px;
  display: flex;
  justify-content: center;
  align-items: center;
  background-image: url(${Background});
  background-size: cover;
`;

const BottomFrame = styled.div`
  width: 900px;
  height: 600px;
  background-color: white;
  display: flex;
  flex-direction: column;
  align-items: center;
  color: #dc1e71;
  h1 {
    margin-top: 10px;
    font-size: 50px;
  }
  h2 {
    font-size: 28px;
  }
  img {
    width: 300px;
    height: 300px;
    margin: 20px 0;
    border-radius: 50%;
    border: 3px solid #ff69b4;
  }
  p {
    font-size: 22px;
    font-weight: 600;
  }
`;

const Home = () => {
  return (
    <HomeFrame>
      <TopFrame>
        <MiddleFrame>
          <BottomFrame>
            <h1>💗4ever1💗</h1>
            <img src={HomeIcon} alt="홈아이콘" />
            <h2>Front-end</h2>
            <p>💗 심지원 💗 이희진 💗 이영우 💗</p>
            <br />
            <h2>Back-end</h2>
            <p>💗 김지민 💗 신대경(👑) 💗 오수빈 💗 김희성 💗</p>
          </BottomFrame>
        </MiddleFrame>
      </TopFrame>
    </HomeFrame>
  );
};

export default Home;
