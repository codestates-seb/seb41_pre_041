import styled from "styled-components";
import { Link } from "react-router-dom";

const HeaderWrap = styled.div`
  display: flex;
  position: fixed;
  justify-content: center;
  z-index: 100;
  width: 100%;
  border-top: 3px solid #f2740d;
  background-color: #f8f9f9;
  box-shadow: rgb(0 0 0 / 5%) 0px 1px 2px, rgb(0 0 0 / 5%) 0px 1px 4px,
    rgb(0 0 0 / 5%) 0px 2px 8px; ;
`;

const HeaderContainer = styled.div`
  display: flex;
  min-width: 1264px;
  height: 50px;

  .logo {
    width: 166px;
    height: 100%;
    padding: 0px 8px;
    display: flex;
    align-items: center;
  }

  .logo-img {
    display: inline-block;
    width: 150px;
    height: 30px;
    margin-top: -4px;
    background: url(https://cdn.sstatic.net/Img/unified/sprites.svg?v=fcc0ea44ba27)
      0px -500px no-repeat;
  }

  .logo:hover {
    background: #e3e6e8;
  }

  .nav-items {
    display: flex;
    li {
      padding: 6px 12px;
    }
    a {
      line-height: 36px;
      font-size: 13px;
      color: #525960;
    }
    li:hover {
      background: #e3e6e8;
    }
  }

  /* search */
  #search {
    display: flex;
    align-items: center;
    flex-grow: 1;
    padding: 0 8px;
  }
  .input-wrap {
    position: relative;
    flex: 1;
    .icon {
      position: absolute;
      top: 50%;
      left: 1%;
      margin-top: -8px;
      fill: #838c95;
    }
  }
  input {
    width: 100%;
    height: 30px;
    line-height: 30px;
    padding-left: 30px;
  }

  /* login */
  .nav-login {
    display: flex;
    align-items: center;
    .members {
      display: flex;
      align-items: center;
      height: 50px;
      padding: 0px 10px;
    }
    .members-icon {
      fill: #525960;
    }
    .members:hover {
      background: #e3e6e8;
      .members-icon {
        fill: #232629;
      }
      span {
        color: #232629;
      }
    }
    .my {
      display: flex;
      span {
        line-height: 50px;
        padding-left: 8px;
        color: #525960;
      }
    }

    .non-members {
      height: 30px;
      padding: 1px 10px;
      border: 1px solid #39739d;
      border-radius: 4px;
      background: #e1ecf4;
    }
    .non-members:last-child {
      margin-left: 4px;
      background: #0a95ff;
      a {
        color: #ffffff;
      }
    }
    .non-members:hover {
      background: #b3d3ea;
    }
    .non-members:last-child:hover {
      background: #0074cc;
    }
    a {
      font-size: 13px;
      color: #39739d;
    }
  }

  .wd38 {
    width: 38px;
  }
`;

function Header({ isLogin }) {
  return (
    <HeaderWrap>
      <HeaderContainer>
        {/* logo */}
        <h1 className="logo">
          <Link to="/">
            <span className="logo-img" />
          </Link>
        </h1>

        {/* navigation */}
        {isLogin ? (
          <ul className="nav-items">
            <li>
              <Link to="/questions">Products</Link>
            </li>
          </ul>
        ) : (
          <ul className="nav-items">
            <li>
              <Link to="/notfound">About</Link>
            </li>
            <li>
              <Link to="/questions">Products</Link>
            </li>
            <li>
              <Link to="/notfound">For Teams</Link>
            </li>
          </ul>
        )}

        {/* search */}
        <form id="search" role="search">
          <div className="input-wrap">
            <input type="text" placeholder="Search..."></input>
            <svg className="icon" width="18" height="18" viewBox="0 0 18 18">
              <path d="m18 16.5-5.14-5.18h-.35a7 7 0 1 0-1.19 1.19v.35L16.5 18l1.5-1.5ZM12 7A5 5 0 1 1 2 7a5 5 0 0 1 10 0Z"></path>
            </svg>
          </div>
        </form>

        {/* login */}
        {isLogin ? (
          <ul className="nav-login">
            <li className="members">
              <Link className="my" to="/">
                <svg
                  className="members-icon"
                  width="24"
                  heigth="24"
                  viewBox="0 0 1000 1000"
                >
                  <path d="M500,10C227,10,10,227,10,500s217,490,490,490s490-217,490-490S773,10,500,10z M500,206c77,0,140,63,140,140c0,77-63,140-140,140c-77,0-140-63-140-140C360,269,423,206,500,206z M801,773c-77,77-182,133-301,133s-224-49-301-133c-21-21-21-56,0-77c77-84,182-140,301-140s224,56,301,140C822,717,822,752,801,773z" />
                </svg>
                <div>
                  <span>1</span>
                </div>
              </Link>
            </li>
            <li className="members">
              <Link to="/">
                <svg
                  className="members-icon"
                  width="20"
                  height="18"
                  viewBox="0 0 20 18"
                >
                  <path d="M4.63 1h10.56a2 2 0 0 1 1.94 1.35L20 10.79V15a2 2 0 0 1-2 2H2a2 2 0 0 1-2-2v-4.21l2.78-8.44c.25-.8 1-1.36 1.85-1.35Zm8.28 12 2-2h2.95l-2.44-7.32a1 1 0 0 0-.95-.68H5.35a1 1 0 0 0-.95.68L1.96 11h2.95l2 2h6Z"></path>
                </svg>
              </Link>
            </li>
            <li className="members">
              <Link to="/">
                <svg
                  className="members-icon"
                  width="18"
                  height="18"
                  viewBox="0 0 18 18"
                >
                  <path d="M15 2V1H3v1H0v4c0 1.6 1.4 3 3 3v1c.4 1.5 3 2.6 5 3v2H5s-1 1.5-1 2h10c0-.4-1-2-1-2h-3v-2c2-.4 4.6-1.5 5-3V9c1.6-.2 3-1.4 3-3V2h-3ZM3 7c-.5 0-1-.5-1-1V4h1v3Zm8.4 2.5L9 8 6.6 9.4l1-2.7L5 5h3l1-2.7L10 5h2.8l-2.3 1.8 1 2.7h-.1ZM16 6c0 .5-.5 1-1 1V4h1v2Z"></path>
                </svg>
              </Link>
            </li>
            <li className="members">
              <Link to="/">
                <svg
                  className="members-icon"
                  width="18"
                  height="18"
                  viewBox="0 0 18 18"
                >
                  <path d="M9 1C4.64 1 1 4.64 1 9c0 4.36 3.64 8 8 8 4.36 0 8-3.64 8-8 0-4.36-3.64-8-8-8Zm.81 12.13c-.02.71-.55 1.15-1.24 1.13-.66-.02-1.17-.49-1.15-1.2.02-.72.56-1.18 1.22-1.16.7.03 1.2.51 1.17 1.23ZM11.77 8c-.59.66-1.78 1.09-2.05 1.97a4 4 0 0 0-.09.75c0 .05-.03.16-.18.16H7.88c-.16 0-.18-.1-.18-.15.06-1.35.66-2.2 1.83-2.88.39-.29.7-.75.7-1.24.01-1.24-1.64-1.82-2.35-.72-.21.33-.18.73-.18 1.1H5.75c0-1.97 1.03-3.26 3.03-3.26 1.75 0 3.47.87 3.47 2.83 0 .57-.2 1.05-.48 1.44Z"></path>
                </svg>
              </Link>
            </li>
            <li className="members">
              <Link to="/">
                <svg
                  className="members-icon"
                  width="18"
                  height="18"
                  viewbox="0 0 18 18"
                >
                  <path d="m8.9844-0.013672a1 1 0 0 0 -0.98438 1.0137v0.38281l-0.55273-0.27734a1 1 0 0 0 -0.48242 -0.11133 1 1 0 0 0 -0.41211 1.9004l1.4473 0.72266v3.6523l-3.1621-1.8262 0.097656-1.6152a1 1 0 0 0 -0.95117 -1.0742 1 1 0 0 0 -1.0449 0.95508l-0.037109 0.61719-0.33008-0.19141a1 1 0 0 0 -0.57422 -0.14062 1 1 0 0 0 -0.42578 1.8711l0.33203 0.19141-0.51758 0.3418a1 1 0 1 0 1.1016 1.668l1.3516-0.89258 3.1621 1.8262-3.1621 1.8262-1.3516-0.89258a1 1 0 0 0 -0.56445 -0.17383 1 1 0 0 0 -0.53711 1.8418l0.51758 0.3418-0.33203 0.19141a1 1 0 1 0 1 1.7305l0.33008-0.19141 0.037109 0.61719a1 1 0 1 0 1.9961 -0.11914l-0.097656-1.6152 3.1621-1.8262v3.6523l-1.4473 0.72266a1 1 0 0 0 0.89453 1.7891l0.55273-0.27734v0.38281a1 1 0 1 0 2 0v-0.38281l0.55273 0.27734a1 1 0 1 0 0.89453 -1.7891l-1.4473-0.72266v-3.6523l3.1621 1.8262-0.097656 1.6152a1 1 0 1 0 1.9961 0.11914l0.037109-0.61719 0.33008 0.19141a1 1 0 1 0 1 -1.7305l-0.33203-0.19141 0.51758-0.3418a1 1 0 0 0 -0.56641 -1.8418 1 1 0 0 0 -0.53516 0.17383l-1.3516 0.89258-3.1621-1.8262 3.1621-1.8262 1.3516 0.89258a1 1 0 1 0 1.1016 -1.668l-0.51758-0.3418 0.33203-0.19141a1 1 0 0 0 -0.45508 -1.8711 1 1 0 0 0 -0.54492 0.14062l-0.33008 0.19141-0.037109-0.61719a1 1 0 0 0 -0.97461 -0.95508 1 1 0 0 0 -1.0215 1.0742l0.097656 1.6152-3.1621 1.8262v-3.6523l1.4473-0.72266a1 1 0 1 0 -0.89453 -1.7891l-0.55273 0.27734v-0.38281a1 1 0 0 0 -1.0156 -1.0137z"></path>
                </svg>
              </Link>
            </li>
            <li className="members">
              <Link to="/">
                <svg
                  className="members-icon"
                  width="18"
                  height="18"
                  viewBox="0 0 18 18"
                >
                  <path d="M15 1H3a2 2 0 0 0-2 2v2h16V3a2 2 0 0 0-2-2ZM1 13c0 1.1.9 2 2 2h8v3l3-3h1a2 2 0 0 0 2-2v-2H1v2Zm16-7H1v4h16V6Z"></path>
                </svg>
              </Link>
            </li>
            <li className="members wd38">
              <Link to="/logout">
                <svg
                  className="members-icon"
                  width="28"
                  height="28"
                  viewBox="0 0 50 30"
                >
                  <path d="m 18,24 0,4 -14,0 0,-24 14,0 0,4 4,0 0,-8 -22,0 0,32 22,0 0,-8 z m -6,-4.003 0,-8 12,0 0,-4 8,8 -8,8 0,-4 z"></path>
                </svg>
              </Link>
            </li>
          </ul>
        ) : (
          <ul className="nav-login">
            <li className="non-members">
              <Link to="/login">Log in</Link>
            </li>
            <li className="non-members">
              <Link to="/signup">Sign Up</Link>
            </li>
          </ul>
        )}
      </HeaderContainer>
    </HeaderWrap>
  );
}

export default Header;
