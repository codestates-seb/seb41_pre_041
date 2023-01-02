import styled from "styled-components";

function PaginationQ({ totalQuestions, limit, page, setPage }) {
  const numPages = Math.ceil(totalQuestions / limit);

  return (
    <>
      <Nav>
        <Button onClick={() => setPage(page - 1)} disabled={page === 1}>
          prev
        </Button>
        {Array(numPages)
          .fill()
          .map((_, i) => (
            <Button
              key={i + 1}
              onClick={() => setPage(i + 1)}
              aria-current={page === i + 1 ? "page" : null}
            >
              {i + 1}
            </Button>
          ))}
        <Button onClick={() => setPage(page + 1)} disabled={page === numPages}>
          next
        </Button>
      </Nav>
    </>
  );
}

const Nav = styled.nav`
  display: flex;
  justify-content: flex-start;
  align-items: center;
  gap: 7px;
  margin: 16px;
`;

const Button = styled.button`
  border: none;
  border-radius: 5px;
  border: 1px solid grey;
  padding: 8px;
  background-color: white;
  color: black;
  margin: 0;
  font-size: 1rem;

  &:hover {
    background: rgb(239, 107, 29);
    cursor: pointer;
  }
`;

export default PaginationQ;
