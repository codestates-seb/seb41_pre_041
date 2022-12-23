import React, { useState } from 'react';
import Pagination from 'react-js-pagination';
import styled from 'styled-components';

const Paging = () => {
    const [page, setPage] = useState(1);
    const handlePageChange = page => {
        setPage(page);
    };

    return (
        <PaginationBox>
            <Pagination
                activePage={page}
                itemsCountPerPage={15}
                totalItemsCount={50}
                pageRangeDisplayed={5}
                prevPageText="Prev"
                nextPageText="Next"
                hideFirstLastPages={true}
                onChange={handlePageChange}
            /> </PaginationBox>
    );
};
const PaginationBox = styled.div`
  .pagination { display: flex; justify-content: start; margin-top: 20px; margin-left: 20px;}
  ul { list-style: none; padding: 0; }
  ul.pagination li {
    display: inline-block;
    width: 30px;
    height: 30px;
    border: 1px solid #e2e2e2;
    border-radius: 5px;
    display: flex;
    justify-content: center;
    align-items: center;
    font-size: 1rem; 
    margin: 1px;
  }
  ul.pagination li:first-child{ width:45px; }
  ul.pagination li:last-child{ width:45px; }
  ul.pagination li a { text-decoration: none; color: black; }
  ul.pagination li.active a { color: white; }
  ul.pagination li.active { background-color: rgb(239, 107, 29); }
  ul.pagination li a:hover,
  ul.pagination li a.active { color: black; }
`
export default Paging;