function [ eigv,w ] = gauss_legendre( n )
%GAUSS_LEGENDRE Summary of this function goes here
%   Detailed explanation goes here

    C = sparse(n, n);
    for i = 1 : n-1 
        b = i / sqrt(4*i^2 - 1);
        C(i+1,i) = b;
        C(i,i+1) = b;
    end
    
    eigv = sort(qr_iteration(C,2,100,1.0e-10,[]));
    
    n=n-1;
    
    w = zeros(n+1,1);
    for i = 1 : n+1
        p_prev = 0; % p_{-1}
        p_crnt = 1; % p_0
    
        xi = eigv(i);
        
        for j = 1 : n+1
           tmp = ((2*j - 1) * xi * p_crnt - (j-1) * p_prev) / j; 
           p_prev = p_crnt; 
           p_crnt = tmp;

        end
        
        w(i) = 2 * (1-xi^2) / (n*(xi * p_crnt - p_prev))^2;
    end
    
end
