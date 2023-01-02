import styled from "styled-components";

const PageWrap = styled.nav`
  margin: 10px auto;
`;

const PageUl = styled.ul`
  float: left;
  list-style: none;
  text-align: center;
  border-radius: 4px;
  color: #ffffff;
  padding: 2px;

  border: 4px 0px solid #186ead;
  background: #0a95ff;
`;

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
    background: #0074cc;
  }
  &:focus {
    color: #ffffff;
    background: #0074cc;
  }
`;

const PageSpan = styled.span`
  &:hover::after,
  &:focus::after {
    border-radius: 100%;
    color: #ffffff;
    background: #0074cc;
  }
`;

const PaginationA = ({ postsPerPage, totalPosts, paginate }) => {
  const pageNumbers = [];
  for (let i = 1; i <= Math.ceil(totalPosts / postsPerPage); i++) {
    pageNumbers.push(i);
  }

  return (
    <>
      <PageWrap>
        <PageUl className="pagination">
          {pageNumbers.map((number) => (
            <Pageli key={number} className="page-item">
              <PageSpan onClick={() => paginate(number)} className="page-link">
                {number}
              </PageSpan>
            </Pageli>
          ))}
        </PageUl>
      </PageWrap>
    </>
  );
};

export default PaginationA;
