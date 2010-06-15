function [ eigv,w ] = gauss_legendre( n )
%GAUSS_LEGENDRE Summary of this function goes here
%   Detailed explanation goes here
    n = n-1;
    C = sparse(n+1,n+1);
    for i = 1 : n 
        b = n / sqrt(4*n^2 - 1);
        C(i+1,i) = b;
        C(i,i+1) = b;
    end
    
    eigv = qr_iteration(C,2,100,1.0e-10,[]);
    
    w = zeros(n+1,1);
    p_prev = 0;
    p_crnt = 1;
    
    for i = 1 : n 
       tmp = ((2*i - 1) * eigv(i) * p_crnt - (i-1) * p_prev) / i;
       p_prev = p_crnt;
       p_crnt = tmp;
    end
    
    
    for i = 1 : n+1
        w(i) = 2 * (1-eigv(i)^2) / (n*(eigv(i) * p_crnt - p_prev))^2;
    end
    
end
