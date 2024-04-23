import { Link } from "react-router-dom";
import { useForm } from "react-hook-form";

import { FormContainer } from "src/components";

import { ROUTE_PATHS } from "src/constants";
import { registerFormFields } from "src/components/forms/fields";

import "./style.scss";

const RegisterForm = () => {
  const methods = useForm({
    mode: "onBlur"
  });

  const onSubmit = (data) => {
    console.log(data);
  }

  return (
    <div className="register-form-container">
      <h5 className="form-title">REGISTER</h5>
      <div className="register-form">
        <FormContainer 
          formFields={ registerFormFields } 
          onSubmit={ methods.handleSubmit(onSubmit) }
          buttonLabel="REGISTER"
          methods={ methods }
        />
        <div className="form-link body-bold">
          <span>Already have an account? </span>
          <Link to={ ROUTE_PATHS.LOGIN }>
            <span className="login">Login</span>
          </Link>
        </div>
      </div>
    </div>
  )
}

export default RegisterForm
