import React from 'react'
import googleImg from '/src/assets/images/google.png'
import kakaoImg from '/src/assets/images/kakao.png'
import naverImg from '/src/assets/images/naver.png'
import styles from '../auth/SocialLoginButtons.module.css'

const SocialLoginButtons = ({title = ""}) => {
    const backendUrl = import.meta.env.VITE_API_BASE_URL;

    return (
        <div className={styles.wrapper}>
            <div className={styles.textWrapper}>
                <hr className={styles.line}></hr>
                <p>{title}</p>
                <hr className={styles.line}></hr>
            </div>
            <div className={styles.snsImgBox}>
                <a href={`${backendUrl}/oauth2/authorization/google`}>
                    <img src={googleImg} alt='구글 로그인' />
                </a>
                <a href={`${backendUrl}/oauth2/authorization/kakao`}>
                    <img src={kakaoImg} alt='카카오 로그인'/>
                </a>
                <a href={`${backendUrl}/oauth2/authorization/naver`}>
                    <img src={naverImg} alt='네이버 로그인'/>
                </a>
            </div>
        </div>
    )
}

export default SocialLoginButtons