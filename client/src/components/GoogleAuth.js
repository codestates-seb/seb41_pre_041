import React from "react";
import SocialButton from './SocialButton';

export default function GoogleAuth() {

    const handleSocialLogin = (user) => {
        console.log(user);
    };

    const handleSocialLoginFailure = (err) => {
        console.error(err);
    };

    return (
        <div>
            <SocialButton
                provider="google"
                appId={process.env.GOOGLE_LOGIN_APP_ID}
                onLoginSuccess={handleSocialLogin}
                onLoginFailure={handleSocialLoginFailure}
            >
                Googleログイン
            </SocialButton>
        </div>
    );
}