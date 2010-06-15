function [ S ] = gauss_legendre_quadrature( f , a, b,n)
%GAUSS_LEGENDRE_QUADRATURE Summary of this function goes here
%   Detailed explanation goes here

    if(n > 20)
        exception = MException('testat1:gauss_legendre_quadrature:InvalidArgumentException','Gauss-Legendre-Quadrature with more than 20 points, is not supported');
        throw(exception)
    end

    %get knots and weights
    [x w] = gl(n);

    S = 0;

    for i = 1:n
        %transformation of knots according to the specified interval: z =
        %((b-a)*x(i)+(a+b))/2
        S = S + f(((b-a)*x(i)+(a+b))/2)*w(i);
    end
    
    S = (b-a)/2*S;

end

