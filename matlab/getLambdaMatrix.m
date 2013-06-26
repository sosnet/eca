function[lambdaMatrix, colinearPointsVector] = getLambdaMatrix(p_grid)

n = length(p_grid(p_grid >0));
lambdaMatrix = zeros(n, n);

colinearPointsVector = zeros(1, 3);
colinearPointsVector2 = zeros(1, 3);
counter = 1;

for i = 1:n
  x1 = 0;
  y1 = 0;
  [x1, y1] = find(p_grid == i);
if (isempty(x1)) x1 = 0; end
if (isempty(y1)) y1 = 0; end
if (size(x1, 1) > 1) x1 = x1(1); end
if (size(y1, 1) > 1) y1 = y1(1); end
  for j = 1:n
    if j == i 
      lambdaMatrix(i, j) = -1;
      continue;
    end

    x2 = 0;
    y2 = 0;
    [x2, y2] = find(p_grid == j);
if (isempty(x2)) x2 = 0; end
if (isempty(y2)) y2 = 0; end
if (size(x2, 1) > 1) x2 = x2(1); end
if (size(y2, 1) > 1) y2 = y2(1); end

    numberOfPoints = 0;
    for k = 1:n
      if k == i || k == j
        continue;
      end

      x3 = 0;
      y3 = 0;
      [x3, y3] = find(p_grid == k);
if (isempty(x3)) x3 = 0; end
if (isempty(y3)) y3 = 0; end
if (size(x3, 1) > 1) x3 = x3(1); end
if (size(y3, 1) > 1) y3 = y3(1); end

      determinante = ones(3,3);
      determinante(1, 1) = x1;
      determinante(1, 2) = x2;
      determinante(1, 3) = x3;

      determinante(2, 1) = y1;
      determinante(2, 2) = y2;
      determinante(2, 3) = y3;

      %determinante(3, 1) = determinante(3, 2) = determinante(3, 3) = 1;

      tmp = int32(det(determinante));

      if (tmp < 0)
        numberOfPoints = numberOfPoints + 1;            
      end

      if (tmp == 0)
        tmp2 = zeros(1, 3);
        tmp2(1, 1) = i;
        tmp2(1, 2) = j;
        tmp2(1, 3) = k;
        colinearPointsVector2(counter, :) = tmp2;  
        counter = counter + 1;      
      end
    end
    lambdaMatrix(i, j) = numberOfPoints;
    colinearPointsVector = unique(sort(colinearPointsVector2, 2), 'rows');
  end 
end



