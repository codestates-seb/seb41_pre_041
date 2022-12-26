import React from 'react';
import FacebookLogin from 'react-facebook-login';

function FacebookAuth() {
    function handleFacebookSuccess(response) {
        console.log(response);
    }

    return (
        <div className="App">
            <FacebookLogin
                appId="YOUR_CLIENT_ID"
                autoLoad={true}
                fields="name,email,picture"
                onSuccess={handleFacebookSuccess}
            />
        </div>
    );
}

export default FacebookAuth;