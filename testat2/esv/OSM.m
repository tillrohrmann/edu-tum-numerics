function [t,y,varargout] = OSM(t0,y0,h,s,method,f,df)
% Performs s time steps of the one-step scheme method to solve the ODE
%
%   y' = f(t,y), 
%   y(t0) = y0,
%
% within the interval [t0,t0+s*h].
%
% f:            Right hand side of the ODE.
% t0:           Initial time.
% y0:           Initial value. 
% h:            Time step size.
% s:            Number of time steps to be performed.
% method:       Method to be used.
% df:           Jacobian of f.
%
% t:            Vector containing the time levels t(i), i=0,...,s.
% y:            Vector containing the solution y(i), i=1,...s.
% varargout:    Variable output argument: Vector containing the number of 
%               Newton or fixed point iterations per time step.

% intialize
t = zeros(s+1,1);
t(1) = t0;
y = zeros(size(y0,1),s+1);
y(:,1) = y0;

% Output the number of Newton or fixed point iterations per time step.
if nargout(method) > 2
    iter = zeros(s,1);
    % The implicit equation is solved by either a fixed point iteration or 
    % a Newton iteration with automatic differentiation. 
    if nargin < 7 
        for k=2:s+1
            [t(k),y(:,k),iter(k-1)] = method(t(k-1),y(:,k-1),h,f);
        end
    % The implicit equation is solved by a Newton iteration. 
    % The jacobian is given.         
    else
        for k=2:s+1
            [t(k),y(:,k),iter(k-1)] = method(t(k-1),y(:,k-1),h,f,df);
        end
    end
    varargout(1) = {iter};
% Don't output the number of Newton or fixed point iterations per time 
% step.    
else
    if nargin <7
        for k=2:s+1
            [t(k),y(:,k)] = method(t(k-1),y(:,k-1),h,f);
        end
    else
         for k=2:s+1
            [t(k),y(:,k)] = method(t(k-1),y(:,k-1),h,f,df);
         end
    end
        
end
