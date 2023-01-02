import axios from "../../api/axios";
import { useState, useEffect } from "react";
import styled from "styled-components";

const VoteBtn = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: space-around;
  padding-right: 16px;

  .btn_frame {
    height: 36px;
    background: inherit;
    border: none;
    padding: 0;
    overflow: visible;
    cursor: pointer;
  }

  .count {
    font-size: 21px;
    color: #6a737c;
  }

  .icon {
    cursor: pointer;
  }
`;

const AnswerVoteForm = ({id, voteCount, voteA, setVoteA }) => { 
    const [isVotedA, setIsVotedA] = useState("");

    /*답변 투표 여부 체크*/
    const checkVoteA = async () => {
        await axios
            .get(`api/answers/${id}/votes/me`)
            .then((response) => {
                setIsVotedA(response.data.voteStatus);
            })
            .catch((error) => {
                console.log(error);
            });
    };
        
    /*답변 투표(post) 또는 재투표(patch)*/
    const handleVoteClickA = (e) => { 
        /*첫 투표*/
        if (isVotedA === "" && e.target.id) {
            postVoteadA(e.target.id);
        }
        /*재투표(표 변경)*/ 
        else if (isVotedA !== e.target.id) {
            patchVoteadA(e.target.id);
        }
        /*재투표(표 취소:: 현재 투표 상태와 누른 버튼 id가 동일할 때)*/ 
        else {
            patchVoteadA("NO_VOTE");
        }
    }

    const postVoteadA = async (click) => {
        await axios
            .post(`api/answers/${id}/votes`, {voteStatus: click})
            .then(() => {
                checkVoteA();
                setVoteA(voteA + 1);
            })
            .catch((error) => {
                console.log(error)
            });
    };
    
    const patchVoteadA = async (click) => {
        await axios
            .patch(`api/answers/${id}/votes/me`, {voteStatus: click})
            .then(() => {
                checkVoteA();
                setVoteA(voteA + 1);
            })
            .catch((error) => {
                console.log(error)
            });
    };

    /*투표 정보 즉각 업데이트 (투표 post 또는 patch 할 때마다 발생하도록)*/
    useEffect(() => {
        checkVoteA();
      }, [isVotedA]);

    return (
    <VoteBtn>
        <div className="btn_frame">
            <svg className="icon" width="36" height="36" viewBox="0 0 36 36" fill= {isVotedA === "UPVOTE" ? "#f48225" : "#babfc4"}>
                <path id="UPVOTE" d="M2 25h32L18 9 2 25Z"
                    onClick={
                        (e) => {
                            handleVoteClickA(e)
                        }
                }></path>
        </svg>
    </div>
    <div className="count">{voteCount}</div>
    <div className="btn_frame">
        <svg className="icon" width="36" height="36" viewBox="0 0 36 36" fill= {isVotedA === "DOWNVOTE" ? "#f48225" : "#babfc4"}>
            <path id="DOWNVOTE" d="M2 11h32L18 27 2 11Z"
                onClick={
                    (e) => {
                        handleVoteClickA(e)
                    }
            }></path>
    </svg>
</div>
</VoteBtn>)
};
export default AnswerVoteForm;