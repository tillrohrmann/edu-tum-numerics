function [ S ] = gauss_legendre_quadrature( f , a, b,n)
    % calculates an approximation of the integral of the function f 
    % between a and b using gauss-legendre quadrature with n knots.


    %get knots and weights
    if(n > 20)
        warning('More than 20 points specified. Using gauss_legendre instead of gl');
        [x w] = gauss_legendre(n);
    else
        [x w] = gl(n);
    end


    S = 0;

    h = (b-a) / 2;
    t = (a+b) / 2;
    for i = 1:n
        %transformation of knots according to the specified interval: z =
        %((b-a)*x(i)+(a+b))/2
        S = S + f(h*x(i)+t) * w(i);
    end
    
    S = h * S;

end

