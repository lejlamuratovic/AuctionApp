import { rules } from "src/forms/rules";

export const personalInformationFormFields = [
  {
    label: "First Name",
    type: "text",
    name: "firstName",
    rules: {
        ...rules.required("First name"),
        ...rules.minLength(2, "First name")
    }
  },
  {
    label: "Last Name",
    type: "text",
    name: "lastName",
    rules: {
      ...rules.required("Last name"),
      ...rules.minLength(2, "Last name")
    }
  },
  {
    label: "Email",
    type: "email",
    name: "email",
    rules: rules.email()
  },
  {
    label: "Date of Birth",
    type: "number",
    name: "month",
    rules: rules.month()
  },
  {
    label: " ",
    type: "number",
    name: "day",
    rules: rules.day()
  },
  {
    label: " ",
    type: "number",
    name: "year",
    rules: rules.year()
  }
];
