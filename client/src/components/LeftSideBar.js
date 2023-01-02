import styled from "styled-components";
import { Link } from "react-router-dom";
import { ReactComponent as Earth } from "../assets/earth.svg";

const SideBarContainer = styled.div`
    width: 164px;
    padding-top: 24px;

    font-size: 13px;
    a {
        display: block;
        color: #525960;
    }
    .home {
        padding: 4px 4px 4px 8px;
    }
    .public {
        margin: 16px 0px 4px 8px;
        
        font-size: 11px;
        color: #525960;
    }
    .public-links {
        padding: 8px 6px 8px 30px;
    }
    .icon {
        display: flex;
        align-items: center;
        padding: 8px 6px 8px 8px;

        font-weight: bold;
    }
    .check {
        background: #f1f2f3;
        border-right: 3px solid #f48225;
        color: #0c0d0e;
    }
    svg { margin-right: 4px; }

    .public-links:hover {
        color: #0c0d0e;
    }
`

function LeftSideBar() {
    return (
        <SideBarContainer>
            <ul>
                <li className="home"><a href="/">Home</a></li>
                <li>
                    <ul>
                        <li className="public">PUBLIC</li>
                        <li><Link className="public-links check icon" to="/questions"><Earth width="16" height="16"/>Questions</Link></li>
                        <li><Link className="public-links" to="/notfound">Tags</Link></li>
                        <li><Link className="public-links" to="/notfound">Users</Link></li>
                        <li><Link className="public-links" to="/notfound">Compaines</Link></li>
                    </ul>
                </li>
            </ul>
        </SideBarContainer>
    )
}

export default LeftSideBar;