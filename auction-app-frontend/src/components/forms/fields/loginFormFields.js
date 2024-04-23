import { validationRules } from 'src/components/forms/rules/validationRules';

export const loginFormFields = [
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
