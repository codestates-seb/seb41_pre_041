import axios from "../api/axios";
import { useNavigate } from "react-router-dom";
import styled from "styled-components";
import faviconSprite from "../assets/faviconSprite.png";

// 현재 사이트와 연동된 서비스 목록
const serviceList = [
  { name: "askubuntu", domain: ".com", link: "https://askubuntu.com/" },
  {
    name: "mathoverflow",
    domain: ".net",
    link: "https://mathoverflow.net/",
  },
  {
    name: "serverfault",
    domain: ".com",
    link: "https://serverfault.com/",
  },
  { name: "stackapps", domain: ".com", link: "https://stackapps.com/" },
  {
    name: "stackexchange",
    domain: ".com",
    link: "https://stackexchange.com/",
  },
  {
    name: "stackoverflow",
    domain: ".com",
    link: "https://stackoverflow.com/",
  },
  { name: "superuser", domain: ".com", link: "https://superuser.com/" },
];

// CSS
const LogoutWrap = styled.div`
  width: 100vw;
  display: flex;
  justify-content: center;
  align-items: center;
  height: calc(100vh - 53px);

  background: #f1f2f3;
`;

const LogoutContainer = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;

  h3 {
    margin-bottom: 24px;
    text-align: center;
    font-size: 21px;
    font-weight: 400;
  }
`;

const Service = styled.div`
  width: 320px;
  padding: 24px;
  border-radius: 8px;
  background: #ffffff;

  .service-list {
    margin-bottom: 12px;
    padding-bottom: 12px;
    border-bottom: 1px solid #d6d9dc;

    a {
      display: flex;
      font-size: 15px;
      color: #0074cc;

      span {
        margin: 4px 4px 6px;
      }
    }
  }

  /* favicon */
  .favicon {
    width: 16px;
    height: 16px;
    margin: 7px 4px 4px 4px;
    background: url(${faviconSprite});
    background-size: 16px 7038px;
  }

  .askubuntu {
    background-position: 0 -360px;
  }

  .mathoverflow {
    background-position: 0 -4032px;
  }

  .serverfault {
    background-position: 0 -5652px;
  }

  .stackapps {
    background-position: 0 -6084px;
  }

  .stackoverflow {
    background-position: 0 -6138px;
  }

  .superuser {
    background-position: 0 -6282px;
  }

  .check {
    display: flex;
    margin-bottom: 12px;
    font-size: 12px;

    input {
      margin-right: 6px;
    }
  }

  button {
    padding: 10px;
    margin-right: 10px;
    border-radius: 4px;
    border: 1px solid transparent;
  }

  .logout {
    color: #ffffff;
    background: #0a95ff;
  }

  .cancel {
    background: #ffffff;
  }

  .alarm {
    margin-top: 32px;
    margin-bottom: 0px;
    font-size: 12px;
    color: #6a737c;
  }
`;

const Logout = ({ setIsLogin }) => {
  const navigate = useNavigate();

  const handleLogout = async () => {
    await axios
      .post("/api/auths/logout", {})
      .then((response) => {
        if (response) {
          window.sessionStorage.removeItem("accessToken");
          window.sessionStorage.removeItem("refreshToken");
          setIsLogin(false);
          navigate("/");
        }
      })
      .catch((error) => {
        console.log(error);
      });
  };
  const handleCancel = () => {
    navigate(-1);
  };

  return (
    <LogoutWrap>
      <LogoutContainer>
        <h3>
          Clicking "Log Out" will log you out of the follwing
          <br />
          domains on this device:
        </h3>
        <Service>
          <ul className="service-list">
            {serviceList.map((service) => {
              return (
                <li key={service.name}>
                  <a href={service.link}>
                    <div className={service.name + " favicon"}></div>
                    <span>{service.name + service.domain}</span>
                  </a>
                </li>
              );
            })}
          </ul>
          <div className="check">
            <input type="checkbox" id="checkAll" />
            <label htmlFor="checkAll">Log Out on all devices</label>
          </div>
          <button className="logout" onClick={() => handleLogout()}>
            Log out
          </button>
          <button className="cancel" onClick={() => handleCancel()}>
            Cancel
          </button>
          <p className="alarm">
            If you’re on a shared computer, remember to log out of your Open ID
            provider (Facebook, Google, Stack Exchange, etc.) as well.
          </p>
        </Service>
      </LogoutContainer>
    </LogoutWrap>
  );
};

export default Logout;
