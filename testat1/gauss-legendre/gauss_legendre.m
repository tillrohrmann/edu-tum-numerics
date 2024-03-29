function [ x,w ] = gauss_legendre( n )
    % Calculates the knots (x) and weights (w) for gauss legendre quadrature 
    % with n knots.
    
    % build the matrix (according to the work sheet) of which the eigenvalues shall be calculated
    C = sparse(n, n);
    for i = 1 : n-1 
        b = i / sqrt(4*i^2 - 1);
        C(i+1,i) = b;
        C(i,i+1) = b;
    end
    
    eigv = sort(qr_iteration(C,2,100,1.0e-10,[]));
    
    w = zeros(n,1);
    
    sumOfAllWeights = 0;
    
    %w(i)==w(n-i) => calculating only half of the weights
    for i = 1:n/2
        
        xi = eigv(i);
        
        %calculating P_{n+1}(x_{i})
        p_prev = 0;
        p_current = 1;
        
        for j = 1:n 
            p_next =((2*j-1)*xi*p_current-(j-1)*p_prev)/j; 
            p_prev = p_current;
            p_current = p_next;
        end
        
        w(i) = 2*(1-xi^2)/((2*n+1)*xi*p_current-n*p_prev)^2;
        
        sumOfAllWeights = sumOfAllWeights + 2*w(i);
        
        w(n-i+1) = w(i);
    end
    
    %sum of all weights has to be 2 => if the number of knots is odd the knot in the middle can be calculated by 2-sumOfAllWeights
    if(mod(n,2)== 1)
        w(floor(n/2)+1) = 2 - sumOfAllWeights;
    end
    
    x = eigv;   
end
