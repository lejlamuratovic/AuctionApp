import { useForm } from "react-hook-form";

import { FormContainer } from "src/components";

import { loginFormFields } from "src/components/forms/fields";

import "./style.scss";

const LoginForm = () => {
  const methods = useForm({
    mode: "onBlur" // validate on blur, when user moves to the next field
  });

  const onSubmit = (data) => {
    console.log(data);
  }
  return (
    <div className="login-form-container">
        <h5 className="form-title">LOGIN</h5>
        <div className="login-form">
          <FormContainer 
            formFields={ loginFormFields } 
            onSubmit={ methods.handleSubmit(onSubmit) } 
            buttonLabel="LOGIN" 
            methods={ methods }
          />
        <span className="forgot-password body-bold">Forgot password?</span>
        </div>
    </div>
  )
}

export default LoginForm
