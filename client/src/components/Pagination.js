import React from "react";
import styled from "styled-components";

const PageUl = styled.ul`
    float: left;
    list-style: none;
    text-align: center;
    border-radius: 3px;
    color: #ffffff;
    padding: 2px;

    border: 3px 0px solid #186ead;
    background: rgba(0, 0, 0, 0.4);
`

const Pageli = styled.li`
    display: inline-block;
    font-size: 16px;
    font-weight: 600;
    padding: 5px;
    border-radius: 5px;
    width: 25px;

    &:hover {
        cursor: pointer;
        color: #ffffff;
        background: #263a6c;
    }
    &:focus {
        color: #ffffff;
        background: #263a6c;
    }
`

const PageSpan = styled.span`
    &:hover::after,
    &:focus::after {
        border-radius: 100%;
        color: #ffffff;
        background: #263a6c;
    }
`

const Pagination = ({ postsPerPage, totalPosts, paginate }) => {
    const pageNumbers = [];
    for (let i = 1; i <= Math.ceil(totalPosts / postsPerPage); i++) {
        pageNumbers.push(i);
    }

    return (
        <>
            <nav>
                <PageUl className="pagination">
                    {pageNumbers.map((number) => (
                        <Pageli key={number} className="page-item">
                            <PageSpan onClick={() => paginate(number)} className="page-link">
                                {number}
                            </PageSpan>
                        </Pageli>
                    ))}
                </PageUl>
            </nav>
        </>
    )
}

export default Pagination;