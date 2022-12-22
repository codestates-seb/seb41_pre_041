import React from "react";
import styled from "styled-components";

const HeaderWrap = styled.div`
    display: flex;
    position: fixed;
    justify-content: center;
    z-index: 10;
    width: 100%;
    border-top: 3px solid #F2740D;
    background-color: #f8f9f9;
    box-shadow: rgb(0 0 0 / 5%) 0px 1px 2px, rgb(0 0 0 / 5%) 0px 1px 4px, rgb(0 0 0 / 5%) 0px 2px 8px;;
`

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
        background: url(https://cdn.sstatic.net/Img/unified/sprites.svg?v=fcc0ea44ba27) 0px -500px no-repeat;
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

    #search {
        display: flex;
        align-items: center;
        flex-grow: 1;
        padding: 0 8px;
    }
    .input-wrap {
        flex: 1;
    }
    input {
        width: 100%;
        height: 30px;
        line-height: 30px;
    }

    .nav-login {
        display: flex;
        align-items: center;
        li {
            height: 30px;
            padding: 1px 10px;
            border: 1px solid #39739d;
            border-radius: 4px;
            background: #e1ecf4;
        }
        li:last-child {
            margin-left: 4px;
            background: #0a95ff;
            a {
                color: #ffffff;
            }
        }
        li:hover {
            background: #b3d3ea;
        }
        li:last-child:hover {
            background: #0074cc;
        }
        a {
            font-size: 13px;
            color: #39739d;
        }
    }
`

function StackHeader() {
    return (
        <HeaderWrap>
            <HeaderContainer>
                {/* logo */}
                <h1 className="logo">
                    <a href="/">
                        <span className="logo-img"/>
                    </a>
                </h1>

                {/* navigation */}
                <ul className="nav-items">
                    <li><a href="/">About</a></li>
                    <li><a href="/">Products</a></li>
                    <li><a href="/">For Teams</a></li>
                </ul>

                {/* search */}
                <form id="search" role="search">
                    <div className="input-wrap">
                        <input type="text" placeholder="Search..."></input>
                    </div>
                </form>

                {/* login */}
                <ul className="nav-login">
                    <li><a href="/">Log in</a></li>
                    <li><a href="/">Sign Up</a></li>
                </ul>
            </HeaderContainer>
        </HeaderWrap>
    )
}

export default StackHeader;