export const requiredRule = (field) => ({
    required: `${field} is required`
});

export const minLengthRule = (minLength, field) => ({
    minLength: {
      value: minLength,
      message: `${field} must be at least ${minLength} characters long`
    }
});

export const maxLengthRule = (maxLength, field) => ({
    maxLength: {
      value: maxLength,
      message: `${field} must be at most ${maxLength} characters long`
    }
});

export const patternRule = (pattern, message) => ({
    pattern: {
      value: pattern,
      message: message
    }
});

export const validationRules = {
    email: {
      ...requiredRule("Email"),
      ...patternRule(/^\S+@\S+\.\S+$/, "Please enter a valid email")
    },
    password: {
      ...requiredRule("Password"),
      ...minLengthRule(8, "Password"),
      ...patternRule(
        /^(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9]).{8,}$/, 
        "Password must contain at least one uppercase letter, one lowercase letter, and one number"
        )
    }
};
