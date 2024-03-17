import "./userStatus.scss";

const UserStatus = ({ isLoggedIn, userName }) => {
  return (
    <div className="user-status body-small-semibold">
      {isLoggedIn ? (
        <span>Hi, {userName}</span>
      ) : (
        <div className="user-status__links">
          <a href="/login">Login</a>
          <span> or </span>
          <a href="/create-account">Create an account</a>
        </div>
      )}
    </div>
  );
};

export default UserStatus;
