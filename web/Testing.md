# Testing

This collects all tests that should be built once I have the MVP

## Global error handling
1. 404 redirects to /
2. 403 deletes session cookie redirects to login page
3. (No permission) Snack bar showing no permission with required permission

## Login Form
1. Username has focus on page load and tab goes through
2. Operations list is shown if operation available, otherwise input is shown
3. All three elements are required
4. Login button is disabled until form request returns
5. Login works: Operation is created or selected, user info retrieved

## Nav Bar
1. Can open and close with button on top
2. Active page is highlighted
3. Navigation works