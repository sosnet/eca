function[pointGrid] = labelPoints(pointGrid, N, K, s, index)

% index = 1;
% 
% N = 10;
% K = 7;
% 
% pointGrid = createGrid(N, K);

grid_x = meshgrid(1:N, 1:N);
grid_y = meshgrid(1:N, 1:N)';
x = grid_x(pointGrid == 1);
y = grid_y(pointGrid == 1);
p = [x,y];

k = convhull(x,y);
s = flipud([x(k), y(k)]);

angles = zeros(K, 1);

v = s(index+1, :) - s(index, :);
pointGrid(s(index, 2), s(index, 1)) = 1;
pointGrid(s(index+1, 2), s(index+1, 1)) = 2;

distances = 0;

for i=1:K
    if p(i,:) == s(index, :)
        continue;
    end
    if p(i,:) == s(index+1, :)
        continue;
    end
    w = p(i,:) - s(index, :);
    angles(i) = acos(dot(v,w) / (norm(v)*norm(w)));
    if angles(i) == 0
      angles(i) = -1;      
      distances(i) = norm(p(i,:) - s(index, :));
    else 
      distances(i) = 0;
    end
 end

[value, index] = sort(angles, 1, 'ascend');
[value2, index2] = sort(distances, 1, 'ascend');

l=3;
j = 1;
for i=1:K
    if angles(index(i)) ~= 0
        pointGrid(y(index(i)), x(index(i))) = l;        
        l = l + 1;
    else 
      if angles(index(i)) == -1
        pointGrid(y(index2(j)), x(index2(j))) = l;
        j = j +1;
        l = l + 1;   
      end
    end    
end
