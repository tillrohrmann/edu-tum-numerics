function J = JAC (f,t,x)
% Computes the jacobian J of a function f : R^n -> R^m.

tol = 1e-12;
maxit = 20;

z = feval(f,t,x);
n = numel(x);
m = numel(z);
J = zeros(m,n);

for k=1:n
    h = zeros(n,1);
    h(k) = 2^(-3);
    y_old = (feval(f,t,x+h) - feval(f,t,x-h)) / (2*h(k));
    h = h/2;
    y = (feval(f,t,x+h) - feval(f,t,x-h)) / (2*h(k));

    it = 1;
    while ( norm(y - y_old,2) > tol ) && ( it < maxit )
        it = it + 1;
        y_old = y;
        h = h/2;
        y = (feval(f,t,x+h) - feval(f,t,x-h)) / (2*h(k));
    end
    J(:,k) = y; 
end
end
