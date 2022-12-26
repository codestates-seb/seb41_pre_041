import React from 'react';
import GitHubLogin from 'react-github-login';

function GithubAuth() {
    function handleGitHubSuccess(response) {
        console.log(response);
    }

    return (
        <div className="App">
            <GitHubLogin
                clientId="YOUR_CLIENT_ID"
                onSuccess={handleGitHubSuccess}
            />
        </div>
    );
}

export default GithubAuth;