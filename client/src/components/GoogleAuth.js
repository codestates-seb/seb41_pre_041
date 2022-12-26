import React from "react";
import GoogleLogin from 'react-google-login';

function GoogleAuth() {
    function handleGoogleSuccess(response) {
        console.log(response);
    }

    return (
        <div className="App">
            <GoogleLogin
                clientId={process.env.REACT_APP_Google}
                onSuccess={handleGoogleSuccess}
            />
        </div>
    );
}

export default GoogleAuth;
