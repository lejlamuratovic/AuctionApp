import { InputField, Button } from "src/components";

import "./style.scss";

const LoginForm = () => {
  return (
    <div className="login-form-container">
      <form action="" className="form-options">
        <h5 className="form-title">LOGIN</h5>
          <div className="form-inputs">
            <InputField label="Email" type="text" id="email" name="email" placeholder="Please enter your email" />
            <InputField  label="Password" type="password" id="password" name="password" placeholder="Please enter your password" />
          </div>
          <Button label="Login" variant="filled" />
          <span className="forgot-password body-bold">Forgot password?</span>
      </form>
    </div>
  )
}

export default LoginForm
