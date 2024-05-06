export const rules = {
    required: (field) => ({
      required: `${field} is required`
    }),
    minLength: (minLength, field) => ({
        minLength: {
            value: minLength,
            message: `${field} must be at least ${minLength} characters long`
        }
    }),
    maxLength: (maxLength, field) => ({
        maxLength: {
            value: maxLength,
            message: `${field} must be at most ${maxLength} characters long`
        }
    }),
    minValue: (minValue, field) => ({
        min: {
            value: minValue,
            message: `${field} must be at least $${minValue}`
        }
    }),
<<<<<<< HEAD
=======
    maxValue: (maxValue, field) => ({
        max: {
            value: maxValue,
            message: `${field} must be at most $${maxValue}`
        }
    }),
>>>>>>> 9013752 (personal information form)
    pattern: (pattern, message) => ({
        pattern: {
            value: pattern,
            message: message
        }
    }),
    email: () => ({
        ...rules.required("Email"),
        ...rules.pattern(/^\S+@\S+\.\S+$/, "Please enter a valid email")
    }),
    password: () => ({
        ...rules.required("Password"),
        ...rules.minLength(8, "Password"),
        ...rules.pattern(
            /^(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9]).{8,}$/, 
            "Password must contain at least one uppercase letter, one lowercase letter, and one number"
        )
    })
};
