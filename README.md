# ssc-y24t2-backend-import-ai-copliot
ssc-y24t2-backend-import-ai-copliot created by GitHub Classroom

Group Name: Import AI.Copilot

Members:

Chinanard Sathitseth 6481366

Hanna Hahn 6481334 

Sareena Aulla 6481197

Haicheng Wang 6580244
- access remote server with passwordless login
ssh newuser@152.42.204.118
- allow ports
  -> http-https
  -> 3306

  
# SSC Project - Git ReadMe

## Project Overview

SSC is an e-commerce platform featuring distinct functionalities for Admins and Buyers. Admins can manage inventory, assign roles, and track orders, while Buyers can browse and purchase products, completing transactions through a credit card payment system.


### Features & TODO List


- Login & Register

- Admin Homepage 

- Buyer Homepage 

- Transaction page

- Shipping Status page
  

### Routes Implementation

Planned Routes:

/login - User authentication page.

/register - Registration page for Buyers.

<ins> login and register in the same page </ins>

/home -> Buyer homepage with product listings.

/admin-home -> Admin dashboard with edit/add products and order tracking and role assignments.

/transaction -> Checkout and payment processing page.

/shipped -> show that order is paid and shipped with "randomized" generated tracking numbers. and "Back to Home" function to redirect to home page

**Rule for Payment**
“Transaction” page will let user fill the address and credit card number
        * only 10 digits allowed for credit cards , if it != 10, then the purchase fail
        * if the card started with 111 ….. , then succeeds
        * if the card started with 000 ….., then fails
        * if the card started with other numbers (123,011,999 … ) , then fails

- - - - - - -
> 16 Mar 2025 
- - - - - - -
