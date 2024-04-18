import { InputField, Button } from "src/components";

import "./style.scss";

const RegisterForm = () => {
  return (
    <div className="form-container">
      <form action="" className="form-options">
        <h5 className="form-title">REGISTER</h5>
          <div className="form-inputs">
            <InputField label="First Name" type="text" id="first-name" name="first-name" placeholder="Please enter your first name" />
            <InputField label="Last Name" type="text" id="last-name" name="last-name" placeholder="Please enter your last name" />
            <InputField label="Email" type="text" id="email" name="email" placeholder="Please enter your email" />
            <InputField  label="Password" type="password" id="password" name="password" placeholder="Please enter your password" />
          </div>
          <Button label="Register" variant="filled" />
          <div className="form-link body-bold">
            <span>Already have an account? </span>
            <span className="login">Login</span>
            </div>
      </form>
    </div>
  )
}

export default RegisterForm
