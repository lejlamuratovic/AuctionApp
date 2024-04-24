import {
    requiredRule,
    minLengthRule,
    validationRules
} from "src/forms/rules/validationRules";

export const registerFormFields = [
  {
    label: "First Name",
    type: "text",
    name: "firstName",
    rules: {
        ...requiredRule("First name"),
        ...minLengthRule(2, "First name")
    }
  },
  {
    label: "Last Name",
    type: "text",
    name: "lastName",
    rules: {
      ...requiredRule("Last name"),
      ...minLengthRule(2, "Last name")
    }
  },
  {
    label: "Email",
    type: "email",
    name: "email",
    rules: { ...validationRules.email }
  },
  {
    label: "Password",
    type: "password",
    name: "password",
    rules: { ...validationRules.password }
  }
];
